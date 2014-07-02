var selfInfo = function(tabId, tabText){
	var currentUserStore = Ext.create('Ext.data.Store', {
		storeId:'currentUserStore',
		autoLoad: true,
		fields:['userId', 'userName', 'email', 'phone', 'role', 'points', 'wwId', 'balance'],
		proxy: {
			type: 'ajax',
			url: 'findUserByExample.action',
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			},
			extraParams: { 'user.userId': userId }
		}
	});
	var currentUser = null;
	currentUserStore.load({
  		id: userId, //set the id here
		scope:this,
		callback: function(records, operation, success){
			if(success){
		    	currentUser = records[0];
		    	var formPanel = Ext.create('Ext.form.Panel', {
					bodyPadding: 5,
					width: 500,
					// The form will submit an AJAX request to this URL when submitted
					url: 'updateUser.action',
		
					// Fields will be arranged vertically, stretched to full width
					layout: 'anchor',
					defaults: {
						anchor: '100%'
					},
					submitEmptyText: false,
					// The fields
					defaultType: 'textfield',
					items: [{
						name: 'user.userId',
						allowBlank: false,
						value: currentUser.data.userId,
						readOnly: true,
						xtype: "hiddenfield"
					},{
						fieldLabel: '昵称',
						name: 'user.userName',
						allowBlank: false,
						value: currentUser.data.userName,
						readOnly: true,
						disabled: true
					},{
						fieldLabel: '旧登录密码',
						name: 'oldPwd',
						inputType: 'password'
						//emptyText: '如果不需要更改登录密码, 留空即可'
					},{
						fieldLabel: '新登录密码',
						name: 'user.password',
						inputType: 'password'
						//emptyText: '如果不需要更改登录密码, 留空即可'
					},{
						fieldLabel: '新登录密码确认',
						name: 'user.passwordConfirm',
						inputType: 'password',
						validator: function() {
			                   var frm = this.up('form').getForm();
			                   // Save the fields we are going to insert values into
			                   var pass1 = frm.findField("user.password").getValue();
			                   var pass2 = frm.findField('user.passwordConfirm').getValue();
			                    if (pass1 == pass2)
			                        return true;
			                    else 
			                        return "密码输入不一致";
			            },
						emptyText: '如果不需要更改登录密码, 全部留空即可'
					},{
						fieldLabel: '邮箱',
						name: 'user.email',
						value: currentUser.data.email,
						allowBlank: false,
						vtype: 'email'
					},{
						fieldLabel: '联系方式',
						name: 'user.phone',
						value: currentUser.data.phone,
						allowBlank: false,
						validator: function() {
			                   var frm = this.up('form').getForm();
			                   // Save the fields we are going to insert values into
			                   var phone = frm.findField("user.phone").getValue();
			                    if (/\d{11}/.test(phone))
			                        return true;
			                    else 
			                        return "电话号码格式错误, 请输入正确的电话号码, 如12312312312";
			            }
					},{
						fieldLabel: '账户余额',
						//name: 'user.balance',
						value: currentUser.data.balance,
						readOnly: true,
						disabled: true
					},{
						fieldLabel: '积分',
						//name: 'user.points',
						value: currentUser.data.points,
						readOnly: true,
						disabled: true
					},{
						fieldLabel: '角色',
						//name: 'user.role',
						allowBlank: false,
						value: currentUser.data.role == "admin"? "管理员": "普通用户",
						readOnly: true,
						disabled: true
					}],
					// Reset and Submit buttons
					buttons: [{
						text: 'Reset',
						handler: function() {
							this.up('form').getForm().reset();
						}
					}, {
						text: 'Submit',
						formBind: true, //only enabled once the form is valid
						disabled: true,
						handler: function() {
							var form = this.up('form').getForm();
							if (form.isValid()) {
								form.submit({
									success: function(form, action) {
									   Ext.Msg.alert('提示', "个人信息更新成功！");
									},
									failure: function(form, action) {
										Ext.Msg.alert('提示', action.result.tip);
									}
								});
							}
						}
					}]
				});
		    	var panel = new Ext.panel.Panel({
		    		bodyPadding: 5,
		    		items: [formPanel]
		    	});
				createTab(tabId, tabText, panel);
		    }
		}
	});
};

var selfRecharge = function(tabId, tabText){
	var selfRechargeStore = Ext.create('Ext.data.Store', {
		storeId:'selfRechargeStore',
		autoLoad: true,
		fields:['rechargeId', 'userId', 'method', 'accountName', 'amount', 'tbOrderId', 'submitTime', 'closeTime', 'state', 'note'],
		proxy: {
			type: 'ajax',
			url: 'findAllRechargeByUser.action',
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			}
		}
	});

	var toolbarSelfRecharge = Ext.create('Ext.toolbar.Toolbar', {
		items: [
			{
				text: '充值',
				iconCls: 'icon-add',
				handler : function(){
					var window = Ext.create('Ext.window.Window', {
						title: '充值信息',
						layout: 'fit',
						modal: true
					}).show();
								
					var formPanel = Ext.create('Ext.form.Panel', {
						bodyPadding: 5,
						width: 500,
						// The form will submit an AJAX request to this URL when submitted
						url: 'saveRecharge.action',
						// Fields will be arranged vertically, stretched to full width
						layout: 'anchor',
						defaults: {
							anchor: '100%'
						},
			
						// The fields
						defaultType: 'textfield',
						items: [
						{
							fieldLabel: "用户编号",
							value: userId,
							name: 'recharge.userId',
							readOnly: true
						},{
							xtype: "combo",
							fieldLabel: "充值方式",
               				store: new Ext.data.SimpleStore({
								data: [
									['支付宝', '支付宝'],
									['中国银行', '中国银行'],
									['工商银行', '工商银行'],
									['建设银行', '建设银行'],
									['农业银行', '农业银行'],
								],
								fields: ['value', 'text']
							}),
							value: '支付宝',
							valueField: 'value',
							displayField: 'text',
							name: 'recharge.method',
							allowBlank: false
						},{
							fieldLabel: '账户名称',
							name: 'recharge.accountName',
							allowBlank: false
						},{
							fieldLabel: '充值金额',
							name: 'recharge.amount',
							allowBlank: false
						},{
							fieldLabel: '淘宝订单编号',
							name: 'recharge.tbOrderId',
							allowBlank: false
						},{
							fieldLabel: '提交日期',
							name: 'recharge.submitTime',
							value: Ext.Date.format(new Date(), "Y-m-d\\TH:i:s"),
							allowBlank: false,
							readOnly: true
						},{
							xtype: 'textareafield',
							fieldLabel: '备注',
							name: 'recharge.note',
							allowBlank: true
						},{
							xtype: 'hiddenfield',
							name: 'recharge.state',
							value: '已提交',
							readOnly: true
						}],
						id: "rechargeForm",
						// Reset and Submit buttons
						buttons: [{
							text: 'Reset',
							handler: function() {
								this.up('form').getForm().reset();
							}
						}, {
							text: 'Submit',
							formBind: true, //only enabled once the form is valid
							disabled: true,
							handler: function() {
								var form = this.up('form').getForm();
								if (form.isValid()) {
									var validate_pwd_window =  new Ext.Window({
										width: 300,
				             			resizable : false,
			             				modal : true,
			             				title : '请输入支付密码',
			             				items: [ 
			             				         new Ext.FormPanel({
			             				        	url: 'checkPayPwd.action',
			             				        	defaults: {
			             				        		anchor: '100%'
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
			             												var rechargeForm = Ext.getCmp("rechargeForm");
		             													rechargeForm.submit({
		             														success: function(form, action) {
		             															Ext.Msg.alert('提示', "充值已提交！");
		             															validate_pwd_window.close();
		             															window.close();
		             															selfRechargeStore.reload();
		             														},
		             														failure: function(form, action) {
		             															Ext.Msg.alert('提示', "充值提交失败！");
		             														}
		             													});
			             											},
			             											failure: function() {
			             												Ext.Msg.alert("提示", "支付密码错误！");
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
							}
						}]
					});
					window.add(formPanel);
				}
			}, {
				text : '充值说明'
			}, {
				xtype: 'datefield',
                id: 'recharge_search_time_from',
                value: new Date(),
                format:'Y-m-d',
                submitFormat:'Y-m-d'
            }, '', {
				xtype: 'datefield',
                id: 'recharge_search_time_to',
                value: new Date(),
                format:'Y-m-d',
                submitFormat:'Y-m-d'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls : 'icon-search',
                handler: function() {
					selfRechargeStore.load({params : { from: Ext.getCmp('recharge_search_time_from').getRawValue(), to: Ext.getCmp('recharge_search_time_to').getRawValue() } });
				}
            }, {
				xtype: 'textfield',
                emptyText : '多条件可用逗号或者空格隔开!',
                id: 'recharge_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls : 'icon-search',
                handler: function() {
					selfRechargeStore.load({params : { conditions: Ext.getCmp('recharge_search_text').getValue() } });
				}
            }
		]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: selfRechargeStore,
		columns: [
			{ text: '编号', dataIndex: 'rechargeId', flex: 1 },				
			{ text: '用户编号', dataIndex: 'userId', flex: 1 },
			{ text: '充值方式', dataIndex: 'method', flex: 2 },
			{ text: '账户名称', dataIndex: 'accountName', flex: 2 },
			{ text: '充值金额', dataIndex: 'amount', flex: 2 },
			{ text: '淘宝订单编号', dataIndex: 'tbOrderId', flex: 2 },
			{ text: '提交日期', dataIndex: 'submitTime', flex: 2 },
			{ text: '结算日期', dataIndex: 'closeTime', flex: 2 },
			{ text: '充值状态', dataIndex: 'state', flex: 1 },
			{ text: '备注', dataIndex: 'note', flex: 3 }
		],
		dockedItems: [toolbarSelfRecharge, {
	        xtype: 'pagingtoolbar',
	        store: selfRechargeStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }]
	});
	
	createTab(tabId, tabText, gridPanel);
};

var selfAddr = function (tabId, tabText){
	var selfAddrStore = Ext.create('Ext.data.Store', {
		storeId:'selfAddrStore',
		autoLoad: true,
		fields:['addressId', 'userId', 'province', 'city', 'district', 'street', 'zipCode', 'contact', 'phone', 'shortName'],
		proxy: {
			type: 'ajax',
			url: 'findAllAddress.action',
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			}
		}
	});

	var toolbarSelfAddr = Ext.create('Ext.toolbar.Toolbar', {
		items: [
			{
				text: '添加地址',
				iconCls: 'icon-add',
				handler : function(){
					var window = Ext.create('Ext.window.Window', {
						title: '新地址信息',
						layout: 'fit',
						modal: true
					}).show();
								
					var formPanel = Ext.create('Ext.form.Panel', {
						bodyPadding: 5,
						width: 500,
						// The form will submit an AJAX request to this URL when submitted
						url: 'saveAddress.action',
						// Fields will be arranged vertically, stretched to full width
						layout: 'anchor',
						defaults: {
							anchor: '100%'
						},
			
						// The fields
						defaultType: 'textfield',
						items: [{
							fieldLabel: "用户编号",
							value: userId,
							name: 'address.userId',
							readOnly: true
						},{
							fieldLabel: "地址名称",
							name: 'address.shortName',
							allowBlank: false
						},{
							xtype: "combo",
							fieldLabel: "省",
               				store: new Ext.data.SimpleStore({
								data: [
									['支付宝', '支付宝'],
									['中国银行', '中国银行'],
									['工商银行', '工商银行'],
									['建设银行', '建设银行'],
									['农业银行', '农业银行'],
								],
								fields: ['value', 'text']
							}),
							value: '请选择',
							valueField: 'value',
							displayField: 'text',
							name: 'address.province',
							allowBlank: false
						},{
							fieldLabel: '市',
							name: 'address.city',
							allowBlank: false
						},{
							fieldLabel: '区',
							name: 'address.district',
							allowBlank: false
						},{
							fieldLabel: '街道',
							name: 'address.street',
							allowBlank: false
						},{
							fieldLabel: '邮政编码',
							name: 'address.zipCode',
							allowBlank: false
						},{
							fieldLabel: '收件人',
							name: 'address.contact',
							allowBlank: false
						},{
							fieldLabel: '联系方式',
							name: 'address.phone',
							allowBlank: false
						}],
			
						// Reset and Submit buttons
						buttons: [{
							text: 'Reset',
							handler: function() {
								this.up('form').getForm().reset();
							}
						}, {
							text: 'Submit',
							formBind: true, //only enabled once the form is valid
							disabled: true,
							handler: function() {
								var form = this.up('form').getForm();
								if (form.isValid()) {
									form.submit({
										success: function(form, action) {
										   Ext.Msg.alert('Success', action.result.msg);
										   window.close();
										   selfAddrStore.reload();
										},
										failure: function(form, action) {
											Ext.Msg.alert('Failed', action.result.msg);
										}
									});
								}
							}
						}]
					});
					window.add(formPanel);
				}
			}, {
				text : '删除地址',
				iconCls : 'icon-del',
				handler : function() {
					var record = gridPanel.getSelectionModel().getSelection();
					if (record) {
						Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : 'deleteAddress.action',
									params : {
										"address.addressId" : record[0].getData().addressId
									},
									success : function() {
										Ext.Msg.alert('Success', "删除地址成功！");
										selfAddrStore.reload();
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
					}
				}
			}, {
				xtype: 'textfield',
                emptyText : '多条件可用逗号或者空格隔开!',
                id: 'addr_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls : 'icon-search',
                handler: function() {
                	selfAddrStore.load({params : { conditions: Ext.getCmp('addr_search_text').getValue() } });
				}
            }
		]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: selfAddrStore,
		columns: [
			{ text: '编号', dataIndex: 'addressId', flex: 1 },	
			{ text: '用户编号', dataIndex: 'userId', flex: 1 },
			{ text: '地址名称', dataIndex: 'shortName', editor: {
                xtype: 'textfield',
                allowBlank: false,
                maxLength: 50
            }, flex: 2 },
			{ text: '省', dataIndex: 'province', editor: {
                xtype: 'textfield',
                allowBlank: false,
                maxLength: 50
            }, flex: 1 },
			{ text: '市', dataIndex: 'city', editor: {
                xtype: 'textfield',
                allowBlank: false,
                maxLength: 50
            }, flex: 1 },
			{ text: '区', dataIndex: 'district', editor: {
                xtype: 'textfield',
                allowBlank: false,
                maxLength: 50
            }, flex: 1 },
			{ text: '街道', dataIndex: 'street', editor: {
                xtype: 'textfield',
                allowBlank: false,
                maxLength: 50
            }, flex: 4 },
			{ text: '邮政编码', dataIndex: 'zipCode', editor: {
                xtype: 'textfield',
                allowBlank: false,
                maxLength: 6
            }, flex: 1 },
			{ text: '收件人', dataIndex: 'contact', editor: {
                xtype: 'textfield',
                allowBlank: false,
                maxLength: 50
            }, flex: 2 },
			{ text: '联系方式', dataIndex: 'phone', editor: {
                xtype: 'textfield',
                allowBlank: false,
                maxLength: 20
            }, flex: 2 }
		],
		selType: 'cellmodel',
	    plugins: [
        	Ext.create('Ext.grid.plugin.CellEditing', {
            	clicksToEdit: 1
        	})
        ],
		dockedItems: [toolbarSelfAddr, {
	        xtype: 'pagingtoolbar',
	        store: selfAddrStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }]
	});
	gridPanel.on('edit', function(editor, e) {
		var field = "address."+e.field;
		var p = {};
		p[field] = e.value;
		p["address.addressId"] = e.record.getData().addressId;
	    Ext.Ajax.request({
			url : 'updateAddress.action',
			params : p,
			success : function() {
				//selfAddrStore.reload();
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
	});
	createTab(tabId, tabText, gridPanel);
};

var selfPayPwd = function(tabId, tabText) {
	var formPanel = Ext.create('Ext.form.Panel', {
		bodyPadding: 5,
		width: 500,
		// The form will submit an AJAX request to this URL when submitted
		url: 'updateUser.action',
		// Fields will be arranged vertically, stretched to full width
		layout: 'anchor',
		defaults: {
			anchor: '100%'
		},
		// The fields
		defaultType: 'textfield',
		items: [{
			name: 'user.userId',
			allowBlank: false,
			value: userId,
			readOnly: true,
			xtype: "hiddenfield"
		},{
			fieldLabel: '旧支付密码',
			name: 'oldPayPwd',
			inputType: 'password'
		},{
			fieldLabel: '新支付密码',
			name: 'user.payPwd',
			inputType: 'password'
		},{
			fieldLabel: '新支付密码确认',
			name: 'user.payPwdConfirm',
			inputType: 'password',
			validator: function() {
                   var frm = this.up('form').getForm();
                   // Save the fields we are going to insert values into
                   var pass1 = frm.findField("user.payPwd").getValue();
                   var pass2 = frm.findField('user.payPwdConfirm').getValue();
                    if (pass1 == pass2)
                        return true;
                    else 
                        return "密码输入不一致";
            }
		}],
		// Reset and Submit buttons
		buttons: [{
			text: 'Reset',
			handler: function() {
				this.up('form').getForm().reset();
			}
		}, {
			text: 'Submit',
			formBind: true, //only enabled once the form is valid
			disabled: true,
			handler: function() {
				var form = this.up('form').getForm();
				if (form.isValid()) {
					form.submit({
						success: function(form, action) {
						   Ext.Msg.alert('提示', "支付密码更新成功！");
						},
						failure: function(form, action) {
							Ext.Msg.alert('提示', action.result.tip);
						}
					});
				}
			}
		}]
	});
	var panel = Ext.create('Ext.panel.Panel', {
		bodyPadding: 5,
	    items: [formPanel]
	});	
	createTab(tabId, tabText, panel);
};


var selfOrders = function(tabId, tabText){
	var selfOrderStore = Ext.create('Ext.data.Store', {
		storeId:'selfOrderStore',
		autoLoad: true,
		fields:['orderId', 'userId', 'delivery', 'orderItem', 'submitTime', 'closeTime', 'state', 'note', 'total'],
		proxy: {
			type: 'ajax',
			url: 'findAllOrderByUser.action',
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			}
		}
	});
	
	var toolbarSelfOrder = Ext.create('Ext.toolbar.Toolbar', {
		items: [{
				xtype: 'datefield',
                id: 'manage_selforder_search_time_from',
                value: new Date(),
                format:'Y-m-d',
                submitFormat:'Y-m-d'
            }, '', {
				xtype: 'datefield',
                id: 'manage_selforder_search_time_to',
                value: new Date(),
                format:'Y-m-d',
                submitFormat:'Y-m-d'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls: 'icon-search',
                handler: function() {
					selfOrderStore.load({params: { from: Ext.getCmp('manage_selforder_search_time_from').getRawValue(), to: Ext.getCmp('manage_selforder_search_time_to').getRawValue(), userId: userId } });
				}
            }, {
				xtype: 'textfield',
                emptyText: '输入订单号搜索',
                id: 'manage_selforder_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls: 'icon-search',
                handler: function() {
					selfOrderStore.load({ params: { conditions: Ext.getCmp('manage_selforder_search_text').getValue() } });
				}
            }
		]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: selfOrderStore,
		columns: [
			{ text: '编号', dataIndex: 'orderId', flex: 1 },				
			{ text: '用户编号', dataIndex: 'userId', flex: 1 },
			{ text: '合计', dataIndex: 'total', flex: 1 },
			{ text: '提交日期', dataIndex: 'submitTime', flex: 2 },
			{ text: '结算日期', dataIndex: 'closeTime', flex: 2 },
			{ text: '订单状态', dataIndex: 'state', flex: 1 },
			{ text: '备注', dataIndex: 'note', flex: 2 }
		],
		dockedItems: [toolbarSelfOrder, {
	        xtype: 'pagingtoolbar',
	        store: selfOrderStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }],
	    listeners: {
	    	itemdblclick: function (view, record, item, index, e, eOpts){
	    		var amountObj = {}, itemIdStr = "";
	    		var orderItemStr = record.get('orderItem');
	    		if (!orderItemStr || orderItemStr == ''){
	    			Ext.Msg.alert("提示", "没有订单信息！");
	    			return;
	    		}
	    		var items = orderItemStr.split(",");
	    		for (var i = 0; i < items.length; i++){
	    			var item = items[i].split("_");
	    			amountObj[item[0]] = item[1];
	    			itemIdStr += item[0];
	    			if (i != items.length - 1){
	    				itemIdStr += ",";
	    			}
	    		}
	    		Ext.define('FenXiao.model.OrderItem', {
	                extend: 'Ext.data.Model',
	                fields: [
	                         { name: 'subjectId', type: 'number' }, 
	                         { name: 'novid', type: 'string' },
	                         { name: 'channel', type: 'string' }, 
	                         { name: 'sizeone', type: 'string' },
	                         { name: 'sizetwo', type: 'string' },
	                         { name: 'tagprice',  type: 'number' }, 
	                         { name: 'discount', type: 'number' },
	                         { name: 'brand', type: 'string' },
	                         { name: 'largeclass', type: 'string' },
	                         { name: 'styles', type: 'string' },
	                         { name: 'categoryId', type: 'string' },
	                         { name: 'color', type: 'string' },
	                         { name: 'object', type: 'string' },
	                         { name: 'subjectName', type: 'string' },
	                         { name: 'seasons', type: 'string' },
	                         { name: 'series', type: 'string' },
	                         { name: 'sex', type: 'string' },
	                         { name: 'year', type: 'string' },
	                         { name: 'remarks', type: 'string' },
	                         { name: 'province', type: 'string' },
	                         { name: 'newNovid', type: 'string' },
	                         { name: 'monthl', type: 'string' },
	                         { name: 'numbers', type: 'string' },
	                         { name: 'total', type: 'string' },
	                         { name: 'amount', type: 'number', convert: function(v, record){
					        	return amountObj[record.get('subjectId')]; }}, 
					         { name: 'total', type: 'number', convert: function (v, record) {
				             	return Math.round(record.get('tagprice') * record.get('discount') * record.get('amount') * 100) / 100;}}
	                ]
	            });
	    		var orderItemsStore = Ext.create("Ext.data.Store", {
	    			autoLoad: true,
	    			/*fields:['subjectId', 'novid', 'brand', 'sizeone', 'sizetwo', 'largeclass', 'styles', 'categoryId', 'color', 'object', 
	        		        'subjectName', 'tagprice', 'discount', 'seasons', 'series', 'sex', 'year', 'remarks', 'province', 'channel', 'newNovid', 'monthl', 'numbers', 'total'],*/
	    			model: 'FenXiao.model.OrderItem',
	    			proxy: {
	    				type: 'ajax',
	    				url: 'findCommodityByIds.action',
	    				reader: {
	    					type: 'json',
	    					root: 'root',
	    					totalProperty: 'totalProperty'
	    				},
	    				extraParams: {
	    					conditions: itemIdStr
	    				}
	    			},
	    			listeners: {
	    				load: function(){
	    					var old = Ext.getCmp('order-' + record.get('orderId'));
	    		    		if (old) {
	    		    			old.show();
	    		    		} else {
		    					var orderItemsPanel = Ext.create("Ext.grid.Panel", {
		    					    store: orderItemsStore,
		    						columns: [
	    						          {text: '数量', dataIndex: 'amount'},                                     
								          {text: '渠道名称', dataIndex: 'channel'},                                     
								          {text: '货号', dataIndex: 'novid'},
								          {text: '新货号', dataIndex: 'newNovid'},
								          {text: '货号名称', dataIndex: 'subjectName'}, 
								          {text: '品牌', dataIndex: 'brand'}, 
								          {text: '尺码1', dataIndex: 'sizeone'}, 
								          {text: '尺码2', dataIndex: 'sizetwo'}, 
								          {text: '大类', dataIndex: 'largeclass'}, 
								          {text: '款型', dataIndex: 'styles'}, 
								          {text: '颜色', dataIndex: 'color'},
								          {text: '项目', dataIndex: 'object'},
								          {text: '吊牌价', dataIndex: 'tagprice'},
								          {text: '季节', dataIndex: 'season'},
								          {text: '性别', dataIndex: 'sex'},
								          {text: '年份', dataIndex: 'year'},
								          {text: '折扣', dataIndex: 'discount'},
								          {text: '月份', dataIndex: 'monthl'},
								          {text: '备注', dataIndex: 'remarks'}
		    						],
		    						dockedItems: [{
		    					        xtype: 'pagingtoolbar',
		    					        store: orderItemsStore,   // same store GridPanel is using
		    					        dock: 'bottom',
		    					        displayInfo: true
		    					    }]
		    		    		});
		    		    		createTab("order-" + record.get('orderId'), "订单-" + record.get('orderId'), orderItemsPanel);
	    		    		}
	    				}
	    			}
	    		});
	    	}
	    }
	});
	
	createTab(tabId, tabText, gridPanel);
};

/////////////////////反馈查询

var selfExpress = function(tabId, tabText){
	var selfExpressStore = Ext.create('Ext.data.Store', {
		storeId:'selfExpressStore',
		autoLoad: true,
		fields:['feedbackId', 'dingdanhao', 'danhao', 'yunfei', 'zhekou', 'jinou', 'sku', 'sizeone', 'sizetwo', 'numberl', 'commodity', 'price', 'methods', 'address', 'userName', 'phone', 'zipcode', 'channels','sku', 'leaf', 'remarks', 'userid', 'submitTime'],
		proxy: {
			type: 'ajax',
			url: 'findAllFeedbackByUser.action',
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			}
		}
	});
	
	var toolbarSelfOrder = Ext.create('Ext.toolbar.Toolbar', {
		items: [{
				xtype: 'textfield',
                emptyText: '输入订单号搜索',
                id: 'manage_selfexpress_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls: 'icon-search',
                handler: function() {
					selfExpressStore.load({ params: { conditions: Ext.getCmp('manage_selfexpress_search_text').getValue() } });
				}
            }
		]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: selfExpressStore,
		columns: [
			{ text: '订单号', dataIndex: 'dingdanhao', flex: 1 },				
			{ text: '用户编号', dataIndex: 'userid', flex: 1 },
			{ text: '快递单号', dataIndex: 'danhao', flex: 1 },
			{ text: '快递公司', dataIndex: 'methods', flex: 2 },
			{ text: '商品名称', dataIndex: 'commodity', flex: 2 },
			{ text: '货号', dataIndex: 'sku', flex: 2 },
			{ text: '下单日期', dataIndex: 'submitTime', flex: 2}
			
		],
		dockedItems: [toolbarSelfOrder, {
	        xtype: 'pagingtoolbar',
	        store: selfExpressStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }]
	});
	
	createTab(tabId, tabText, gridPanel);
};
