var prodSearch = function(id, text) {
	var panel = Ext.create('Ext.panel.Panel', {
		layout: 'column',
		bodyPadding: 5,
	    items: [{
	        xtype: 'textfield',
	        id: 'prodSearchText',
            emptyText : '请输入货号'
	    }, {
	        xtype: 'button',
	        text: '查询',
	        iconCls: 'icon-search',
	        handler: function() {
	        	if (Ext.getCmp('prodSearchText').getValue().trim() == ''){
					Ext.Msg.alert('提示', '请输入货号!');
	        		return;
	        	}
	        	var prodSearchStore = Ext.create('Ext.data.Store', {
	        		autoLoad: true,
	        		fields:['subjectId', 'novid', 'brand', 'sizeone', 'sizetwo', 'largeclass', 'styles', 'categoryId', 'color', 'object', 
	        		        'subjectName', 'tagprice', 'discount', 'seasons', 'series', 'sex', 'year', 'remarks', 'province', 'channel', 'newNovid', 'monthl', 'numbers', 'total'],
	        		proxy: {
	        			type: 'ajax',
	        			url: 'findCommodityByNovid.action',
	        			reader: {
	        				type: 'json',
	        				root: 'root',
	        				totalProperty: 'totalProperty'
	        			},
	        			extraParams: {
	        				conditions: Ext.getCmp('prodSearchText').getValue().trim()
	        			}
	        		}, 
        			listeners: {
        				load: function(store) {
        					processResult();
        				}
        			}
	        	});
//	        	alert(1)
	        	function processResult (){
		        	var resultArr = [];
		        	prodSearchStore.each(function(record) {
		        		var novid = record.get('novid');
		        		var channel = record.get('channel');
		        		var obj, subobj;
		        		// iterate through result array to check if an obj with the same novid already exists
		        		for (var i = 0; i < resultArr.length; i++){
		        			var result = resultArr[i];
		        			if (result['novid'] == novid){
		        				obj = result;
		        				break;
		        			}
		        		}
		        		// if not exists, create a new obj, assign novid and empty array for channels, push it to result array
			        	if (!obj) {
			        		obj = {
			        			'novid': novid,
			        			'channels' : [],
				        		'tagprice': record.get('tagprice'),
				        		'discount': record.get('discount')
			        		};
				        	resultArr.push(obj);
			        	}
			        	// iterate through channels array to check if an subobj with the same channel already exists
		        		for (var i = 0; i < obj['channels'].length; i++){
		        			if (obj['channels'][i]['channel'] == channel){
		        				subobj = obj['channels'][i];
		        				break;
		        			}
		        		}
		        		// if not, create a new subobj, assign channel and empty array for sizes, push it to obj's channels array
		        		if (!subobj){
		        			subobj = {
		        				'channel': channel,
		    	        		'sizes': []
		        			};
		        			obj['channels'].push(subobj);
		        		}
		        		// add new size to current channel's sizes array 
		        		subobj['sizes'].push({
			        		'size': record.get('sizeone'),
			        		'amount': record.get('numbers'),
			        	});
		        	});
//		        	alert(Ext.encode(resultArr[resultArr.length - 1]));
		        	var resultPanels = [];
		        	var columnArray = {};
		        	for (var i = 0; i < resultArr.length; i++){
		        		var fields = [{ name: 'novid', type: 'string'}, { name: 'channel', type: 'string' }, { name: 'tagprice', type: 'string' }, { name: 'discount', type: 'string'}];
		        		var items = [];
		        		var columns = [{ text: '货号', dataIndex: 'novid' }, { text: '渠道名称', dataIndex: 'channel' }, { text: '吊牌价', dataIndex: 'tagprice' }, { text: '折扣', dataIndex: 'discount'}];
		        		var novid = resultArr[i]['novid'];
		        		var channels = resultArr[i]['channels'];
		        		var pos = 2, sizePos = {};
		        		// build fields and data items dynamically
		        		for (var j = 0; j < channels.length; j++){
		        			var sizes = channels[j]['sizes'];
		        			var data = {
		        				'novid': novid,
		        				'channel': channels[j]['channel'],
				        		'tagprice': resultArr[i]['tagprice'],
				        		'discount': resultArr[i]['discount']
		        			};
		        			for (var k = 0; k < sizes.length; k++){
		        				if (!sizePos[sizes[k]['size']]){
			        				fields.push({ name: sizes[k]['size'], type: 'int' });
			        				columns.push({ text: sizes[k]['size'], dataIndex: sizes[k]['size'] });
			        				sizePos[sizes[k]['size']] = pos++;
		        				}
			        			data[sizes[k]['size']] = sizes[k]['amount'];
			        		}
		        			items.push(data);
		        		}
	        			columnArray[novid] = columns;
	//	        		alert(Ext.encode(items));
	//	        		alert(Ext.encode(fields));
//		        		alert(Ext.encode(columns));
		        		var resultStore = Ext.create('Ext.data.Store', {
		        			storeId: 'resultStore_'+ novid,
		        		    fields: fields,
		        		    data:{'items': items },
		        		    proxy: {
		        		        type: 'memory',
		        		        reader: {
		        		            type: 'json',
		        		            root: 'items'
		        		        }
		        		    }
		        		});
		        		var oldPanel = Ext.getCmp(novid);
		        		if (oldPanel){
		        			oldPanel.close();
		        		}
		        		var gridPanel = Ext.create('Ext.grid.Panel', {
		        			id: novid,
		        			title: novid,
		        		    store: Ext.data.StoreManager.lookup('resultStore_' + novid),
		        		    columns: columnArray[novid],
		        		    layout: 'fit',
		        		    selType: 'cellmodel',
		        		    listeners: {
		        		    	'celldblclick': function( grid, td, cellIndex, record, tr, rowIndex, e, eOpts ){
		        		    		if (cellIndex <= 3) {
		        		    			return;
		        		    		}
		        		    		var size = columnArray[this.getId()][cellIndex]['text'];
		        		    		var value = record.get(size);
		        		    		var novid = record.get('novid');
		        		    		var channel = record.get('channel');
		        		    		var tagprice = record.get('tagprice');
		        		    		var discount = record.get('discount');
		        		    		if (value == 0){
	        							Ext.Msg.alert('提示', '对不起，该款商品暂时缺货！');
	        			        		return;
		        		    		}
		        		    		var singleProdStore = Ext.create('Ext.data.Store', {
//		        		    			storeId:'singleProdStore',
		        		    			autoLoad: true,
		        		    			fields:['subjectId', 'novid', 'brand', 'sizeone', 'sizetwo', 'largeclass', 'styles', 'categoryId', 'color', 'object', 
		        		    			        'subjectName', 'tagprice', 'discount', 'seasons', 'series', 'sex', 'year', 'remarks', 'province', 'channel', 'newNovid', 'monthl', 'numbers', 'total'],
		        		    			proxy: {
		        		    				type: 'ajax',
		        		    				url: 'findExact.action',
		        		    				extraParams: {
	    	        		    				"commodity.sizeone": size,
	    	        		    				"commodity.novid": novid,
	    	        		    				"commodity.channel": channel
		        		    		        },
		        		    				reader: {
		        		    					type: 'json',
		        		    					root: 'root',
		        		    					totalProperty: 'totalProperty'
		        		    				}
		        		    			},
		        		    		    listeners : {
		        		    		        load : function(store) {
		        		    		        	if (singleProdStore.getCount() == 0){
		        		    		        		Ext.Msg.alert("错误提示", "找不到商品信息!");
		        		    		        		return;
		        		    		        	}
		        	        		    		var subjectId = singleProdStore.getAt(0).get('subjectId');
		        	        		    		var add_to_cart_window = Ext.create('Ext.window.Window', {
		        	        		    			title: '添加到购物车',
		        	        		    			width: 500,
		        	        		    			items:[new Ext.form.Panel({
		        	                        			width: 480,
		        	                        			url: 'saveCartItem.action',
		        	                        			defaults: {
		        	                        			    anchor: '100%',
		        	                        			},
		        	                        			layout: 'anchor',
		        	                        	    	defaultType: 'textfield',
		        	                        	    	items: [
		        	                        	    	    {xtype: 'hiddenfield', name: 'cartItem.userId', value: userId},
		        	                        	    	    {xtype: 'hiddenfield', name: 'cartItem.subjectId', value: subjectId},
		        	                        	    	    {fieldLabel: '货号', name: 'cartItem.novid', value: novid, readOnly: true}, 
		        	                        	    	    {fieldLabel: '渠道', name: 'cartItem.channel', value: channel, readOnly: true}, 
		        	                        				{fieldLabel: '尺寸1', name: 'cartItem.size', value: size, readOnly: true }, 
		        	                        				{fieldLabel: '吊牌价', name: 'cartItem.tagprice', value: tagprice, readOnly: true }, 
		        	                        				{fieldLabel: '折扣', name: 'cartItem.discount', value: discount, readOnly: true }, 
		        	                        				{fieldLabel: '数量', name: 'cartItem.amount', xtype: 'numberfield', value: 0, validator: function() {
		        		             			                   var frm = this.up('form').getForm();
		        		            			                   var amount = frm.findField("cartItem.amount").getValue();
		        		            			                    if (amount <= value && amount > 0)
		        		            			                        return true;
		        		            			                    else 
		        		            			                        return "数量超过范围，请重新选择！";
		        		            			            }}
		        	                        			],
		        	                        			buttonAlign: 'center',
		        	                        			minButtonWidth: 60,
		        	                        			buttons: [{
		        	                        				text: '添加',
		        	                        				handler: function(btn) {
		        	                        					var frm = this.up('form').getForm();
		        	                        					if (frm.isValid()) {
		        	                        						frm.submit({
		        	                        							waitTitle: '请稍候',
		        	                        							waitMsg: '正在提交表单数据,请稍候...',
		        	                        							success: function(form, action) {
		        	                        								Ext.Msg.alert( '提示', "商品添加成功");
		        	                        								add_to_cart_window.close();
		        	                        								resultStore.reload();
		        	                        							},
		        	                        							failure: function() {
		        	                        								Ext.Msg.show({
		        	                        									title: '错误提示',
		        	                        									msg: '添加失败!',
		        	                        									buttons: Ext.Msg.OK,
		        	                        									icon: Ext.Msg.ERROR
		        	                        								});
		        	                        							}
		        	                        						});
		        	                        					}
		        	                        				}
		        	                        			}, {
		        	                        				text: '重置',
		        	                        				handler: function() {
		        	                        					this.up('form').getForm().reset();
		        	                        				}
		        	                        			}, {
		        	                        				text: '取消',
		        	                        				handler: function() {
		        	                        					add_to_cart_window.close();
		        	                        				}
		        	                        			}]
		        	                        		})] 
		        	        		    		}).show();
		        		    		        }
		        		    		    }
		        		    		});
		        		    	}
		        		    }
		        		});
//		        		alert(3)
		        		resultPanels.push(gridPanel);
		        	}
		        	var resultPanel = Ext.create('Ext.panel.Panel', {
		        	    title: '查询结果',
		        	    layout: 'fit',
		        	    items: [{
		        	    	xtype: 'container',
		        	    	layout: 'fit',
		        	    	items: [{
		        	            xtype: 'container',
		        	            layout: 'fit',
		        	            items: [{
		        	               xtype: 'container',
		        	               layout: {
		        	            	   type: 'vbox',
		        	            	   align: 'stretch'
		        	               },
		        	               items: resultPanels
		        	           }]
		        	       }]
		        	  }]
		        	});
		        	var old = Ext.getCmp("prodSearchResults");
		        	if (old){
		        		old.close();
		        	}
		        	createTab("prodSearchResults", "查询结果", resultPanel, 'auto');
	        	}
	        }
	    }]
	});
	createTab(id, text, panel);
};

var shoppingCart = function(tabId, tabText){
	var shoppingCartStore = Ext.create('Ext.data.Store', {
		storeId:'shoppingCartStore',
		autoLoad: true,
		fields:['cartItemId', 'userId', 'subjectId', 'novid', 'channel', 'size', 'tagprice', 'discount', 'amount', 'time'],
		proxy: {
			type: 'ajax',
			url: 'findbyCartItem.action',
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			}
		}
	});
	
	var toolbarShoppingCart = Ext.create('Ext.toolbar.Toolbar', {
		items: [{
			text : '删除商品',
			iconCls : 'icon-del',
			handler : function() {
				var record = gridPanel.getSelectionModel().getSelection();
				if (record && record.length > 0) {
					var ids = '';
					for(var i = 0; i < record.length; i++){
						ids += record[i].getData().cartItemId;
						if(i != record.length - 1){
							ids += ',';
						}
					}
					Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : 'deleteCartItem.action',
								params : {
									"ids" : ids
								},
								success : function() {
									Ext.Msg.alert('提示', "删除地址成功！");
									shoppingCartStore.reload();
								},
								failure : function() {
									Ext.Msg.show({
										title : '错误提示',
										msg : '删除时发生错误!',
										buttons : Ext.Msg.OK,
										icon : Ext.Msg.ERROR
									});
								}
							});
						}
					});
				} else {
					Ext.Msg.alert('提示', "请选择记录！");
				}
			}
		}, '-', {
			text : '结算',
			iconCls : 'icon-plugin',
			handler : function() {
				var records = gridPanel.getSelectionModel().getSelection();
				if (records && records.length > 0) {
					var ids = '';
					for(var i = 0; i < records.length; i++){
						ids += records[i].getData().cartItemId;
						if(i != records.length - 1){
							ids += ',';
						}
					}
					var yunfeiOrdersStore = Ext.create('Ext.data.Store', {
						autoLoad: true,
						fields:['freightid', 'priovice', 'firstfreight', 'lastfreight','freightcompany','channel'],
						proxy: {
							type: 'ajax',
							url: 'findAllFreight.action',
							reader: {
								type: 'json',
								root: 'root',
								totalProperty: 'totalProperty'
							}
						}
					});
					var discountsStore = Ext.create('Ext.data.Store', {
						autoLoad: true,
						fields:['channel', 'discount', 'discountId', 'userId'],
						proxy: {
							type: 'ajax',
							url: 'findDiscountByExample.action',
							reader: {
								type: 'json',
								root: 'root',
								totalProperty: 'totalProperty'
							},
							extraParams: {
								"discount.userId": userId
							}
						},
						listeners: {
							load : function(store){
								process();
							}
						}
					});
					
					var process = function(){
						Ext.define('FenXiao.model.Checkout', {
			                extend: 'Ext.data.Model',
			                fields: [
								 { name: 'cartItemId', type: 'string' }, 
								 { name: 'userId', type: 'number' },
								 { name: 'subjectId', type: 'number' }, 
								 { name: 'novid', type: 'string' },
								 { name: 'channel', type: 'string' }, 
								 { name: 'size', type: 'string' },
								 { name: 'tagprice',  type: 'number' }, 
								 { name: 'discount', type: 'number' },
								 { name: 'personalDiscount', type: 'number', convert: function (v, record){
									 // alert(discountsStore.getCount());
									 var dis = record.get("discount");
									 discountsStore.each(function(rec){
										if (rec.get("channel") == record.get("channel")){
											dis = rec.get("discount");
										}
									});
									 return dis;
								 }},
								 { name: 'amount', type: 'number' }, 
								 { name: 'time', type: 'date' },
								 { name: 'deliveryFee', type: 'number' },
								 { name: 'total', type: 'number', convert: function (v, record) {
									 var dis = record.get("discount");
									 discountsStore.each(function(rec){
										if (rec.get("channel") == record.get("channel")){
											dis = rec.get("discount");
										}
									});
							 		return Math.round(record.get('tagprice') * dis * record.get('amount') * 100) / 100;
								 }}
			                ]
			            });
						var dataConverted = [];
						for (var i = 0; i < records.length; i++){
							dataConverted.push(Ext.create('FenXiao.model.Checkout', records[i].getData()));
						}
						var checkoutStore = Ext.create('Ext.data.Store', {
							storeId:'checkoutStore',
							fields: [
							         { name: 'cartItemId', type: 'string' }, 
							         { name: 'userId', type: 'number' },
							         { name: 'subjectId', type: 'number' }, 
							         { name: 'novid', type: 'string' },
							         { name: 'channel', type: 'string' }, 
							         { name: 'size', type: 'string' },
							         { name: 'tagprice',  type: 'number' }, 
							         { name: 'discount', type: 'number' },
							         { name: 'personalDiscount', type: 'number' },
							         { name: 'amount', type: 'number' }, 
							         { name: 'time', type: 'date' },
							         { name: 'total', type: 'number' },
							         { name: 'deliveryFee', type: 'number' },
						        ],
						    data: dataConverted,
						    autoLoad: true
						});
						
						var toolbarCheckout = Ext.create('Ext.toolbar.Toolbar', {
							items: [/*{
								xtype: "combo",
	               				store: Ext.create('Ext.data.Store', {
	               					fields:['addressId', 'userId', 'province', 'city', 'district', 'street', 'zipCode', 'contact', 'phone', 'shortName'],
	               					proxy: {
	               						type: 'ajax',
	               						url: 'findAllAddress.action',
	               						reader: {
	               							type: 'json',
	               							root: 'root'
	               						}
	               					}
	               				}),
								valueField: 'addressId',
								displayField: 'shortName',
								emptyText: "请选择收货地址",
								id: 'sel_address',
								allowBlank: false,
								listeners: {
									beforeselect: function( combo, record, index, eOpts ){
										alert(record.get('province'));
									}
								}
							},*/ {
								xtype: "combo",
	               				store: new Ext.data.SimpleStore({
									data: [
										['河北省', '河北省'],
										['山西省', '山西省'],
										['辽宁省', '辽宁省'],
										['吉林省', '吉林省'],
										['黑龙江省', '黑龙江省'],
										['江苏省', '江苏省'],
										['浙江省', '浙江省'],
										['安徽省', '安徽省'],
										['福建省', '福建省'],
										['江西省', '江西省'],
										['山东省', '山东省'],
										['河南省', '河南省'],
										['湖北省', '湖北省'],
										['湖南省', '湖南省'],
										['广东省', '广东省'],
										['海南省', '海南省'],
										['四川省', '四川省'],
										['贵州省', '贵州省'],
										['云南省', '云南省'],
										['陕西省', '陕西省'],
										['甘肃省', '甘肃省'],
										['青海省', '青海省'],
										['台湾省', '台湾省'],
										['内蒙古自治区', '内蒙古自治区'],
										['广西壮族自治区', '广西壮族自治区'],
										['西藏自治区', '西藏自治区'],
										['宁夏回族自治区', '宁夏回族自治区'],
										['新疆维吾尔自治区', '新疆维吾尔自治区'],
										['香港特别行政区', '香港特别行政区'],
										['澳门特别行政区', '澳门特别行政区']
									],
									fields: ['value', 'text']
								}),
								emptyText: '请选择省份',
								valueField: 'value',
								displayField: 'text',
								id: 'order-province',
								allowBlank: false
							}, {
								xtype: 'combo',
								store: new Ext.data.SimpleStore({
									  fields: [ "value", "text" ],
									  data: [
									    [ "普通快递", "普通快递" ],
									    [ "顺丰速运", "顺丰速运" ],
									  ]
									}),
								valueField: 'value',
								displayField: 'text',
								emptyText: '请选择快递公司',
								id: 'order-deliver',
								allowBlank: false,
								listeners: {
									select: function ( combo, records, eOpts ) {
										//fields:['freightid', 'priovice', 'firstfreight', 'lastfreight','freightcompany','channel'],
										var msg = "";
										var province = Ext.getCmp("order-province").getValue();
										if (null == province) {
											combo.clearValue();
											combo.applyEmptyText();
											combo.getPicker().getSelectionModel().doMultiSelect([], false);
											Ext.Msg.alert("提示", "请选择省!");
											return;
										}
										checkoutStore.each(function(rec){
											// alert(1)
											var queried = yunfeiOrdersStore.queryBy(function (rcd, id){
												//alert(rcd.get("priovice") + " " + Ext.getCmp("order-province").getValue() + " " + rcd.get("channel") + " " + rec.get("channel"));
												return rcd.get("priovice") == province && rec.get("channel") == rcd.get("channel"); 
											});
											// alert(records[0]);
											var delivery = queried.get(0);
											var amount = rec.get("amount");
											var fee = 0;
											if (records[0].get("value") == "顺丰速运"){
												msg = "运费到付";
												fee = 0;
											} else {
												//alert(delivery)
												msg += delivery.get("channel") + "到" + delivery.get("priovice") + "：首重" + delivery.get("firstfreight") + "元，续重：" + delivery.get("lastfreight") + "元<br>";
												// alert(msg);
												if (amount > 1){
													fee = delivery.get("firstfreight") * 1 + (amount - 1) * delivery.get("lastfreight");
												} else {
													fee = delivery.get("firstfreight") * amount;
												}
											}
											rec.set("deliveryFee", fee);
										});
										Ext.Msg.show({
											title: '提示',
											msg: msg,
											buttons: Ext.MessageBox.OK
										});
									}
								}
							}, '-', {
								xtype: 'textfield',
								emptyText: '请输入收货人姓名',
								width: 100,
								id: 'order-receiver',
								maxLength: 10,
								allowBlank: false
							}, '-', {
								xtype: 'textfield',
								emptyText: '请输入收货人手机',
								width: 100,
								id: 'order-cell',
								maxLength: 11,
								allowBlank: false
							}, '-', {
								xtype: 'textfield',
								emptyText: '请输入省级以下收货地址',
								width: 200,
								id: 'order-address',
								maxLength: 200,
								allowBlank: false
							}, '-', {
								xtype: 'textfield',
								emptyText: '请输入备注(可选)',
								width: 300,
								id: 'order-note',
								maxLength: 200
							}, '-', {
								text : '提交订单',
								iconCls : 'icon-plugin',
								handler : function() {
									var deliver = Ext.getCmp('order-deliver').getValue();
									var cell = Ext.getCmp('order-cell').getValue();
									var receiver = Ext.getCmp('order-receiver').getValue();
									var province = Ext.getCmp('order-province').getValue();
									var address = Ext.getCmp('order-address').getValue();
									if (!deliver || deliver.trim() == ""){
										Ext.Msg.alert("提示", "请选择快递公司!");
										return;
									}
									if (!receiver || receiver.trim() == ""){
										Ext.Msg.alert("提示", "请输入收货人姓名!");
										return;
									}
									if (!cell || cell.trim() == ""){
										Ext.Msg.alert("提示", "请输入收货人手机!");
										return;
									}
									if (!province || province.trim() == ""){
										Ext.Msg.alert("提示", "请选择省份!");
										return;
									}
									if (!address || address.trim() == ""){
										Ext.Msg.alert("提示", "请输入收货地址!");
										return;
									}
									var validate_pwd_window =  new Ext.Window({
										width: 300,
				             			resizable : false,
			             				modal : true,
			             				title : '请输入支付密码',
			             				items: [ 
			             				         new Ext.FormPanel({
			             				        	url: 'checkPayPwd.action',
			             				        	defaults: {
			             				        		anchor: '100%',
			             				        	},
			             				        	layout: 'anchor',
			             				        	defaultType: 'textfield',
			             				        	items: [{
			             				        		name: 'user.payPwd',
			             				        		allowBlank: false
			             				        	}],
			             				        	buttons: [{
			             								text: '确认',
			             								handler: function(btn) {
			             									var frm = this.up('form').getForm();
			             									if (frm.isValid()) {
			             										frm.submit({
			             											waitTitle: '请稍候',
			             											waitMsg: '正在提交表单数据,请稍候...',
			             											success: function(form, action) {
			             												Ext.Ajax.request({
			             													url: 'checkBalance.action',
			             													params: {
			             														total: checkoutPanel.getStore().sum('total') + checkoutPanel.getStore().sum('deliveryFee') 
			             													},
			             													success: function(data) {
			             														var response = Ext.decode(data['responseText']);
			             														if (response['success'] == true) {
				             														var orderItems = "", cartItems = "";
						             												for (var i = 0; i < checkoutStore.getCount(); i++){
						             													var item = checkoutStore.getAt(i);
						             													orderItems += item.get('subjectId') + "_" + item.get('amount');
						             													cartItems += item.get('cartItemId');
						             													if (i != checkoutStore.getCount() - 1) {
						             														orderItems += ",";
						             														cartItems += ",";
						             													}
						             												}
						             												Ext.Ajax.request({
						             													url: 'saveOrder.action',
						             													params: {
						             												        "order.state": '已提交',
						             												        "order.submitTime": new Date(),
						             												    	"order.orderItem": orderItems,
						             												    	"order.address": address,
						             												    	"order.receiver": receiver, 
						             												    	"order.cell": cell,
						             												    	"order.province": province,
						             												    	"order.deliveryMethod": deliver,
						             												    	"order.note": Ext.getCmp('order-note').getValue(),
						             												    	"order.total": checkoutPanel.getStore().sum('total'),
						             												    	"order.deliveryFee": checkoutPanel.getStore().sum('deliveryFee'),
						             												    	"cartItemIds": cartItems
						             													},
						             													success: function(response) {
						             														Ext.Msg.alert('提示', "订单提交成功！");
						             														validate_pwd_window.close();
						             														Ext.getCmp("checkout").close();
						             														if (shoppingCartStore) {
						             															shoppingCartStore.reload();
						             														}
						             													},
						             													failure: function(request) {
						             														Ext.Msg.show({
						             															title: '操作提示',
						             															msg: "连接服务器失败",
						             															buttons: Ext.MessageBox.OK,
						             															icon: Ext.MessageBox.ERROR
						             														});
						             													},
						             													method: 'post'
						             												});
			             														} else {
				             														Ext.Msg.alert('提示', "余额不足请充值！");
			             														}
			             													},
			             													failure: function() {
			             														Ext.Msg.alert('提示', "服务器连接失败，请刷新重试！");
			             													}
			             												});
			             											},
			             											failure: function() {
			             												Ext.Msg.show({
			             							    					title : '错误提示',
			             							    					msg : '更新时发生错误!',
			             							    					buttons : Ext.Msg.OK,
			             							    					icon : Ext.Msg.ERROR
			             							    				});
			             											}
			             										});
			             									}
			             								}
			             							}, {
			             								text: '重置',
			             								handler: function() {
			             									this.up('form').getForm().reset();
			             								}
			             							}, {
			             								text: '取消',
			             								handler: function() {
			             									validate_pwd_window.close();
			             								}
			             							}]
			             				        })
			             				]
			             			}).show();
								}
							}]
						});
						var checkoutPanel = Ext.create('Ext.grid.Panel', {
							store: checkoutStore,
							columns: [
								{ text: '货号', dataIndex: 'novid', flex: 2 },
								{ text: '渠道名称', dataIndex: 'channel', flex: 2 },
								{ text: '尺码', dataIndex: 'size', flex: 1 },
								{ text: '吊牌价', dataIndex: 'tagprice', xtype: 'numbercolumn', flex: 1 },
								{ text: '折扣', dataIndex: 'discount', xtype: 'numbercolumn', flex: 1 },
								{ text: '个人折扣', dataIndex: 'personalDiscount', xtype: 'numbercolumn', flex: 1 },
								{ text: '数量', dataIndex: 'amount', editor: {
					                xtype: 'numberfield',
					                allowBlank: false
					            }, flex: 1 },
					            { text: '合计', dataIndex: 'total', xtype: 'numbercolumn', flext: 1, format: '0.00', summaryType: 'sum' },
					            { text: '运费', dataIndex: 'deliveryFee', xtype: 'numbercolumn', flext: 1, format: '0.00', summaryType: 'sum' }
					        ],
					        features: [{
					            ftype: 'summary'
					        }],
							selType: 'cellmodel',
						    plugins: [
					          	Ext.create('Ext.grid.plugin.CellEditing', {
					              	clicksToEdit: 1
					          	})
						    ],
							dockedItems: [toolbarCheckout, {
						        xtype: 'pagingtoolbar',
						        store:  checkoutStore,   // same store GridPanel is using
						        dock: 'bottom',
						        displayInfo: true
						    }],
						    listeners: {
						    	'edit': function(editor, e) {
						    		// update amount
						    		e.record.set('amount', e.value);
						    		
						    		// update total price
						    		e.record.set('total', e.record.get('tagprice') * e.record.get('amount') * e.record.get('personalDiscount'));
						    		
						    		// update delivery fee
						    		var province = Ext.getCmp("order-province");
									var order_delivery = Ext.getCmp("order-deliver");
									if (!province || !province.getValue() || !order_delivery || !order_delivery.getValue()) {
										e.record.set("deliveryFee", 0);
									}
									var queried = yunfeiOrdersStore.queryBy(function (rcd, id){
										return rcd.get("priovice") == province.getValue() && e.record.get("channel") == rcd.get("channel"); 
									});
									var delivery = queried.get(0);
									var amount = e.record.get("amount");
									var fee = 0;
									if (order_delivery.getValue() == "顺丰速运"){
										fee = 0;
									} else {
										if (amount > 1){
											fee = delivery.get("firstfreight") * 1 + (amount - 1) * delivery.get("lastfreight");
										} else {
											fee = delivery.get("firstfreight") * amount;
										}
									}
									e.record.set("deliveryFee", fee);
						    	}
						    }
						});
						createTab("checkout", "购物结算", checkoutPanel);
					}
					
				} else {
					Ext.Msg.alert('提示', "请选择需要结算的商品！");
				}
			}
		}]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: shoppingCartStore,
		columns: [
			{ text: '货号', dataIndex: 'novid', flex: 1 },
			{ text: '渠道名称', dataIndex: 'channel', flex: 2 },
			{ text: '尺码', dataIndex: 'size', flex: 1 },
			{ text: '吊牌价', dataIndex: 'tagprice', flex: 1 },
			{ text: '折扣', dataIndex: 'discount', flex: 1 },
			{ text: '数量', dataIndex: 'amount', editor: {
                xtype: 'numberfield',
                allowBlank: false
            }, flex: 1 },
            { text: '添加时间', dataIndex: 'time', flex: 2 }
		],
		selModel: Ext.create('Ext.selection.CheckboxModel',{mode: "SIMPLE"}),
		selType: 'cellmodel',
	    plugins: [
        	Ext.create('Ext.grid.plugin.CellEditing', {
            	clicksToEdit: 1
        	})
        ],
		dockedItems: [toolbarShoppingCart, {
	        xtype: 'pagingtoolbar',
	        store: shoppingCartStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }],
	    listeners: {
	    	'edit': function(editor, e) {
	    		var field = "cartItem."+e.field;
	    		var p = {};
	    		p[field] = e.value;
	    		p["cartItem.cartItemId"] = e.record.getData().cartItemId;
	    	    Ext.Ajax.request({
	    			url : 'updateCartItem.action',
	    			params : p,
	    			success : function() {
	    				//selfAddrStore.reload();
	    			},
	    			failure : function() {
	    				Ext.Msg.show({
	    					title : '错误提示',
	    					msg : '更新时发生错误!',
	    					buttons : Ext.Msg.OK,
	    					icon : Ext.Msg.ERROR
	    				});
	    			}
	    		});
	    	}
	    }
	});
	createTab(tabId, tabText, gridPanel);
};