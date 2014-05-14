var userInfo = function(tabId, tabText){
	var userInfoStore = Ext.create('Ext.data.Store', {
		storeId:'userStore',
		autoLoad: true,
		fields:['userId', 'userName', 'email', 'phone', 'role', 'points', 'wwId', 'balance'],
		proxy: {
			type: 'ajax',
			url: 'findAllUser.action',
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			}
		}
	});
	
	var toolbarUserInfo = Ext.create('Ext.toolbar.Toolbar', {
		items: [
			{
				text: '添加用户',
				iconCls: 'icon-add',
				handler: function(){
					var window = Ext.create('Ext.window.Window', {
						title: '添加用户',
						layout: 'fit',
						modal: true
					}).show();
								
					var formPanel = Ext.create('Ext.form.Panel', {
						bodyPadding: 5,
						width: 500,
						// The form will submit an AJAX request to this URL when
						// submitted
						url: 'saveUser.action',
						// Fields will be arranged vertically, stretched to full
						// width
						layout: 'anchor',
						defaults: {
							anchor: '100%'
						},
			
						// The fields
						defaultType: 'textfield',
						items: [{
							fieldLabel: '用户名',
							name: 'user.userName',
							allowBlank: false
						},{
							fieldLabel: '密码',
							name: 'user.password',
							inputType: 'password',
							allowBlank: false
						},{
							fieldLabel: '邮箱',
							name: 'user.email',
							allowBlank: false
						},{
							fieldLabel: '联系方式',
							name: 'user.phone',
							allowBlank: false
						},{
							fieldLabel: '角色',
							xtype: "combo",
               				store: new Ext.data.SimpleStore({
								data: [
									['admin', '管理员'],
									['normal', '普通用户']
								],
								fields: ['value', 'text']
							}),
							value: 'normal',
							valueField: 'value',
							displayField: 'text',
							name: 'user.role',
							allowBlank: false
						},{
							xtype: 'numberfield',
							fieldLabel: '积分',
							name: 'user.points',
							value: 0
						},{
							xtype: 'numberfield',
							fieldLabel: '余额',
							name: 'user.balance',
							value: 0.00
						}],
			
						// Reset and Submit buttons
						buttons: [{
							text: 'Reset',
							handler: function() {
								this.up('form').getForm().reset();
							}
						}, {
							text: 'Submit',
							formBind: true, // only enabled once the form is
											// valid
							disabled: true,
							handler: function() {
								var form = this.up('form').getForm();
								if (form.isValid()) {
									form.submit({
										success: function(form, action) {
										   Ext.Msg.alert('Success', action.result.msg);
										   window.close();
										   Ext.data.StoreManager.lookup('userStore').reload();
										},
										failure: function(form, action) {
											Ext.Msg.alert('Failed', action.result.msg);
										}
									});
								}
							}
						}],
					});
					window.add(formPanel);
				}
			},
			{
				text: '编辑用户',
				iconCls: 'icon-edit',
				handler: function(){
							var record = gridPanel.getSelectionModel().getSelection()[0];
							if(record){
								var window = Ext.create('Ext.window.Window', {
									title: '编辑用户',
									layout: 'fit',
									modal: true
								}).show();
											
								var formPanel = Ext.create('Ext.form.Panel', {
									bodyPadding: 5,
									width: 500,
									// The form will submit an AJAX request to
									// this URL when submitted
									url: 'updateUser.action',
									// Fields will be arranged vertically,
									// stretched to full width
									layout: 'anchor',
									defaults: {
										anchor: '100%'
									},
						
									// The fields
									defaultType: 'textfield',
									items: [{
										fieldLabel: '用户ID',
										value: record.getData().userId,
										name: 'user.userId',
										readOnly: true
									},{
										fieldLabel: '用户名',
										value: record.getData().userName,
										name: 'user.userName',
										readOnly: true
									},{
										fieldLabel: '邮箱',
										name: 'user.email',
										value: record.getData().email,
										allowBlank: false
									},{
										fieldLabel: '联系方式',
										name: 'user.phone',
										value: record.getData().phone,
										allowBlank: false
									},{
										fieldLabel: '角色',
										name: 'user.role',
										value: record.getData().role,
										allowBlank: false
									},{
										xtype: 'numberfield',
										fieldLabel: '积分',
										value: record.getData().points,
										name: 'user.points'
									},{
										xtype: 'numberfield',
										fieldLabel: '余额',
										value: record.getData().balance,
										name: 'user.balance'
									}],
						
									// Reset and Submit buttons
									buttons: [{
										text: 'Reset',
										handler: function() {
											this.up('form').getForm().reset();
										}
									}, {
										text: 'Submit',
										formBind: true, // only enabled once the
														// form is valid
										disabled: true,
										handler: function() {
											var form = this.up('form').getForm();
											if (form.isValid()) {
												form.submit({
													success: function(form, action) {
													   Ext.Msg.alert('Success', action.result.msg);
													   window.close();
													   Ext.data.StoreManager.lookup('userStore').reload();
													},
													failure: function(form, action) {
														Ext.Msg.alert('Failed', action.result.msg);
													}
												});
											}
										}
									}],
								});
								window.add(formPanel);
							}
						}
			},
			{
				text: '删除用户',
				iconCls: 'icon-del',
				handler: function() {
					var record = gridPanel.getSelectionModel().getSelection();
					if (record) {
						Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url: 'deleteUser.action',
									params: {
										userId: record[0].getData().userId
									},
									success: function() {
										userInfoStore.reload();
									},
									failure: function() {
										Ext.Msg.show({
											title: '错误提示',
											msg: '删除时发生错误!',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.ERROR
										});
									}
								});
							}
						});
					}
				}
			}, {
				xtype: 'textfield',
                emptyText: '多条件可用逗号或者空格隔开!',
                id: 'user_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls: 'icon-search',
                handler: function() {
                	userInfoStore.load({params: { conditions: Ext.getCmp('user_search_text').getValue() } });
				}
            }
		]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: userInfoStore,
		columns: [
			{ text: '用户ID', dataIndex: 'userId', flex: 1 },
			{ text: '用户名', dataIndex: 'userName', flex: 3 },
			{ text: '邮箱', dataIndex: 'email', flex: 3 },
			{ text: '联系方式', dataIndex: 'phone', flex: 3 },
			{ text: '角色', dataIndex: 'role', flex: 3 },
			{ text: '积分', dataIndex: 'points', flex: 3},
			{ text: '旺旺ID', dataIndex: 'wwId', flex: 3},
			{ text: '余额', dataIndex: 'balance', flex:3 }
		],
		dockedItems: [toolbarUserInfo,{
	        xtype: 'pagingtoolbar',
	        store: userInfoStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true,
	    }]
	});
	
	createTab(tabId, tabText, gridPanel);
};

var rechargeInfo = function(tabId, tabText){
	var rechargeStore = Ext.create('Ext.data.Store', {
		storeId:'rechargeStore',
		autoLoad: true,
		fields:['rechargeId', 'userId', 'method', 'accountName', 'amount', 'tbOrderId', 'submitTime', 'closeTime', 'state', 'note'],
		proxy: {
			type: 'ajax',
			url: 'findAllRecharge.action',
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			}
		}
	});
	
	var toolbarRechargeInfo = Ext.create('Ext.toolbar.Toolbar', {
		items: [{
				xtype: 'datefield',
                id: 'manage_recharge_search_time_from',
                value: new Date(),
                format:'Y-m-d',
                submitFormat:'Y-m-d'
            }, '', {
				xtype: 'datefield',
                id: 'manage_recharge_search_time_to',
                value: new Date(),
                format:'Y-m-d',
                submitFormat:'Y-m-d'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls: 'icon-search',
                handler: function() {
					rechargeStore.load({params: { from: Ext.getCmp('manage_recharge_search_time_from').getRawValue(), to: Ext.getCmp('manage_recharge_search_time_to').getRawValue(), userId: userId } });
				}
            }, {
				xtype: 'textfield',
                emptyText: '多条件可用逗号或者空格隔开!',
                id: 'manage_recharge_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls: 'icon-search',
                handler: function() {
					rechargeStore.load({params: { conditions: Ext.getCmp('manage_recharge_search_text').getValue() } });
				}
            }, {
             	xtype: 'button',
                text: '通过',
                id: 'btn-approve',
                iconCls: 'icon-add',
                handler: function() {
					var record = gridPanel.getSelectionModel().getSelection()[0];
					if (record && record.getData().state == '已提交') {
						Ext.Ajax.request({
							url: 'updateRecharge.action',
							params: {
						        "recharge.rechargeId": record.getData().rechargeId,
						        "recharge.state": '已通过',
						        "recharge.closeTime": new Date()
						    },
							success: function(response) {
								Ext.Msg.alert('Success', "充值记录更新成功！");
								rechargeStore.reload();
							},
							failure: function(request) {
								Ext.MessageBox.show({
									title: '操作提示',
									msg: "连接服务器失败",
									buttons: Ext.MessageBox.OK,
									icon: Ext.MessageBox.ERROR
								});
							},
							method: 'post'
						});
					}
				}
            }, {
             	xtype: 'button',
                text: '拒绝',
                id: 'btn-reject',
                iconCls: 'icon-del',
                handler: function() {
					var record = gridPanel.getSelectionModel().getSelection()[0];
					if (record && record.getData().state == '已提交'){
						Ext.Ajax.request({
							url: 'updateRecharge.action',
							params: {
						        "recharge.rechargeId": record.getData().rechargeId,
						        "recharge.state": '已拒绝',
						        "recharge.closeTime": new Date()
						    },
							success: function(response) {
								Ext.Msg.alert('Success', "充值记录更新成功！");
								rechargeStore.reload();
							},
							failure: function(request) {
								Ext.MessageBox.show({
									title: '操作提示',
									msg: "连接服务器失败",
									buttons: Ext.MessageBox.OK,
									icon: Ext.MessageBox.ERROR
								});
							},
							method: 'post'
						});
					}
				}
            }
		]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: rechargeStore,
		columns: [
			{ text: '编号', dataIndex: 'rechargeId', flex: 1 },				
			{ text: '用户编号', dataIndex: 'userId', flex: 1 },
			{ text: '充值方式', dataIndex: 'method', flex: 1 },
			{ text: '账户名称', dataIndex: 'accountName', flex: 1 },
			{ text: '充值金额', dataIndex: 'amount', flex: 1 },
			{ text: '淘宝订单编号', dataIndex: 'tbOrderId', flex: 2 },
			{ text: '提交日期', dataIndex: 'submitTime', flex: 2 },
			{ text: '结算日期', dataIndex: 'closeTime', flex: 2 },
			{ text: '充值状态', dataIndex: 'state', flex: 1 },
			{ text: '备注', dataIndex: 'note', flex: 2 }
		],
		dockedItems: [toolbarRechargeInfo, {
	        xtype: 'pagingtoolbar',
	        store: rechargeStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }],
	    listeners: {
	    	itemclick: function ( view, record, item, index, e, eOpts ){
	    		if (record.get('state') != '已提交'){
	    			Ext.getCmp('btn-approve').disable();
	    			Ext.getCmp('btn-reject').disable();
	    		} else {
	    			Ext.getCmp('btn-approve').enable();
	    			Ext.getCmp('btn-reject').enable();
	    		}
	    	}
	    }
	});
	
	createTab(tabId, tabText, gridPanel);
};

var prodInfo = function(tabId, tabText){
	var prodInfoStore = Ext.create('Ext.data.Store', {
		storeId:'prodInfoStore',
		autoLoad: true,
		fields:['subjectId', 'novid', 'brand', 'sizeone', 'sizetwo', 'largeclass', 'styles', 'categoryId', 'color', 'object', 
		        'subjectName', 'tagprice', 'discount', 'seasons', 'series', 'sex', 'year', 'remarks', 'province', 'channel', 'newNovid', 'monthl', 'numbers', 'total'],
		proxy: {
			type: 'ajax',
			url: 'findCommodity.action',
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			}
		}
	});
	
	var toolbarProdInfo = Ext.create('Ext.toolbar.Toolbar', {
		items: [{
                xtype: 'button',
                text: '添加商品',
                iconCls: 'icon-add',
                handler: function() {
                	var window_add_commodity = new Ext.Window({
                		title: '添加商品',
                		layout: "fit",
                		modal: true,
                		items: [new Ext.FormPanel({
                			width: 700,
                			url: 'saveCommodity.action',
                			defaults: {
                				msgTarget: 'side', // 验证信息显示右边
                			    anchor: '100%',
                			},
                			layout: 'column',
                	    	defaultType: 'textfield',
                	    	items: [
                				{fieldLabel: '货号', name: 'commodity.novid', allowBlank: false, columnWidth: .45, padding: 2, padding: 2}, 
                				{fieldLabel: '新货号', name: 'commodity.newNovid', columnWidth: .45, padding: 2},
                				{fieldLabel: '货品名称', name: 'commodity.subjectName', allowBlank: false, maxLength: 25, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '品牌', name: 'commodity.brand', maxLength: 50, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '尺寸1', name: 'commodity.sizeone', maxLength: 50, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '尺寸2', name: 'commodity.sizetwo', maxLength: 25, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '大类', name: 'commodity.largeclass', maxLength: 25, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '款型', name: 'commodity.styles', maxLength: 25, columnWidth: .45, padding: 2},
                				{fieldLabel: '年份', name: 'commodity.year', maxLength: 25, columnWidth: .45, padding: 2},
                				{fieldLabel: '月份', name: 'commodity.monthl', maxLength: 25, columnWidth: .45, padding: 2},
                				{fieldLabel: '渠道名称', name: 'commodity.channel', allowBlank: false, maxLength: 25, columnWidth: .45, padding: 2},
                				{fieldLabel: '颜色', name: 'commodity.color', maxLength: 50, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '项目', name: 'commodity.object', maxLength: 50, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '吊牌价', name: 'commodity.tagprice', maxLength: 25, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '折扣', name: 'commodity.discount', maxLength: 25, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '性别', name: 'commodity.sex', maxLength: 25, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '系列', name: 'commodity.series', maxLength: 25, columnWidth: .45, padding: 2}, 
                				{fieldLabel: '季节', name: 'commodity.season', maxLength: 25, columnWidth: .45, padding: 2},
                				{fieldLabel: '省份', name: 'commodity.province', maxLength: 25, columnWidth: .45, padding: 2},
                				{fieldLabel: '总计', name: 'commodity.total', maxLength: 25, columnWidth: .45, padding: 2},
                				{fieldLabel: '库存', name: 'commodity.numbers', maxLength: 25, columnWidth: .45, padding: 2},
                				{fieldLabel: '备注', xtype: 'textarea', name: 'commodity.remarks', maxLength: 500, columnWidth: .9}
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
                								window_add_commodity.close();
                								prodInfoStore.reload();
                							},
                							failure: function() {
                								Ext.Msg.show({
                									title: '错误提示',
                									msg: '该商品可能已经存在!',
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
                					window_add_commodity.close();
                				}
                			}]
                		})]
                	}).show();
                }
            }, '-', {
				text: '删除商品',
				iconCls: 'icon-del',
				handler: function() {
					var records = gridPanel.getSelectionModel().getSelection();
					if (record && records.length > 0) {
						var ids = '';
						for(var i = 0; i < records.length; i++){
							ids += records[i].getData().subjectId;
							if(i != records.length - 1){
								ids += ',';
							}
						}
						Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url: 'deleteCommodity.action',
									params: {
										ids: ids
									},
									success: function() {
										prodInfoStore.reload();
									},
									failure: function() {
										Ext.Msg.show({
											title: '错误提示',
											msg: '删除时发生错误!',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.ERROR
										});
									}
								});
							}
						});
					}
				}
			}, '-', {
				xtype: 'textfield',
				width: 180,
                emptyText: '多条件可用逗号或者空格隔开!',
                id: 'manage_prod_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls: 'icon-search',
                handler: function() {
					prodInfoStore.load({params: { conditions: Ext.getCmp('manage_prod_search_text').getValue() } });
				}
            }, '-', {
                xtype: 'button',
                text: '修改：<font color=red>点击单元格修改，红色标记为修改后状态</font>',
                iconCls: 'icon-edit',
                handler: function() {
                }
            }, '-', {
            	 xtype: 'button',
                 text: '导入',
                 iconCls: 'icon-plugin',
                 handler: function() {
                	 var import_window =  new Ext.Window({
             			resizable : false,
             				modal : true,
             				title : '请选择导入文件',
             				items: [ 
             				         new Ext.FormPanel({
             				        	url: 'upload.action',
             				        	defaults: {
             				        		anchor: '100%',
             				        	},
             				        	layout: 'anchor',
             				        	defaultType: 'textfield',
             				        	items: [{
             				        		xtype: 'fileuploadfield',
             				        		name: 'excelFile',
             				        		allowBlank: false
             				        	}],
             				        	buttons: [{
             								text: '导入',
             								handler: function(btn) {
             									var frm = this.up('form').getForm();
             									if (frm.isValid()) {
             										frm.submit({
             											waitTitle: '请稍候',
             											waitMsg: '正在提交表单数据,请稍候...',
             											success: function(form, action) {
             												Ext.Msg.alert( '提示', "导入成功");
             												import_window.close();
             												prodInfoStore.reload();
             											},
             											failure: function() {
             												Ext.Msg.show({
             													title: '错误提示',
             													msg: '该商品可能已经存在!',
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
             									import_window.close();
             								}
             							}]
             				        })
             				]
             			}).show();
                 }
            }
		]
	});

	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: prodInfoStore,
		columns: [
		          {text: '渠道名称', dataIndex: 'channel', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20 } },                                     
		          {text: '货号', dataIndex: 'novid', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '新货号', dataIndex: 'newNovid', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '货号名称', dataIndex: 'subjectName', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}}, 
		          {text: '品牌', dataIndex: 'brand', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}}, 
		          {text: '尺码1', dataIndex: 'sizeone', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}}, 
		          {text: '尺码2', dataIndex: 'sizetwo', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}}, 
		          {text: '大类', dataIndex: 'largeclass', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}}, 
		          {text: '款型', dataIndex: 'styles', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}}, 
		          {text: '颜色', dataIndex: 'color', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '项目', dataIndex: 'object', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '吊牌价', dataIndex: 'tagprice', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '季节', dataIndex: 'season', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '性别', dataIndex: 'sex', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '年份', dataIndex: 'year', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '折扣', dataIndex: 'discount', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '总计', dataIndex: 'total', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '月份', dataIndex: 'monthl', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '库存', dataIndex: 'numbers', editor: {xtype: 'textfield', allowBlank: false, maxLength: 20}},
		          {text: '备注', dataIndex: 'remarks', editor: {xtype: 'textfield', allowBlank: false, maxLength: 50}}
		],
		selModel: Ext.create('Ext.selection.CheckboxModel',{mode: "SIMPLE"}),
		selType: 'cellmodel',
	    plugins: [
        	Ext.create('Ext.grid.plugin.CellEditing', {
            	clicksToEdit: 1
        	})
        ],
		dockedItems: [toolbarProdInfo, {
	        xtype: 'pagingtoolbar',
	        store: prodInfoStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true,
	    }]
	});
	

	gridPanel.on('edit', function(editor, e) {
		var field = "commodity." + e.field;
		var p = {};
		p[field] = e.value;
		p["commodity.subjectId"] = e.record.getData().subjectId;
	    Ext.Ajax.request({
			url : 'updateCommodity.action',
			params : p,
			success : function() {
				// selfAddrStore.reload();
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

var orderInfo = function(tabId, tabText){
	var orderStore = Ext.create('Ext.data.Store', {
		storeId:'orderStore',
		autoLoad: true,
		fields:['orderId', 'userId', 'delivery', 'orderItem', 'submitTime', 'closeTime', 'state', 'note', 'total'],
		proxy: {
			type: 'ajax',
			url: 'findAllOrder.action',
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			}
		}
	});
	
	var toolbarOrderInfo = Ext.create('Ext.toolbar.Toolbar', {
		items: [{
				xtype: 'datefield',
                id: 'manage_order_search_time_from',
                value: new Date(),
                format:'Y-m-d',
                submitFormat:'Y-m-d'
            }, '', {
				xtype: 'datefield',
                id: 'manage_order_search_time_to',
                value: new Date(),
                format:'Y-m-d',
                submitFormat:'Y-m-d'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls: 'icon-search',
                handler: function() {
					orderStore.load({params: { from: Ext.getCmp('manage_order_search_time_from').getRawValue(), to: Ext.getCmp('manage_order_search_time_to').getRawValue(), userId: userId } });
				}
            }, {
				xtype: 'textfield',
                emptyText: '输入订单号搜索',
                id: 'manage_order_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls: 'icon-search',
                handler: function() {
					orderStore.load({ params: { conditions: Ext.getCmp('manage_order_search_text').getValue() } });
				}
            }, {
             	xtype: 'button',
                text: '发货',
                id: 'btn-deliver',
                iconCls: 'icon-add',
                handler: function() {
					var records = gridPanel.getSelectionModel().getSelection();
					if (records && records.length > 0) {
						var record = records[0];
						Ext.Ajax.request({
							url: 'updateOrder.action',
							params: {
						        "order.orderId": record.getData().orderId,
						        "order.state": '已发货',
						        "order.closeTime": new Date()
						    },
							success: function(response) {
								Ext.Msg.alert('Success', "订单状态更新成功！");
								orderStore.reload();
							},
							failure: function(request) {
								Ext.Msg.alert('提示', "连接服务器失败");
							},
							method: 'post'
						});
					} else {
						Ext.Msg.alert("提示", "请选择订单！");
					}
				}
            }, {
             	xtype: 'button',
                text: '关闭订单',
                id: 'btn-close',
                iconCls: 'icon-del',
                handler: function() {
                	var records = gridPanel.getSelectionModel().getSelection();
					if (records && records.length > 0) {
						var record = records[0];
						Ext.Ajax.request({
							url: 'updateOrder.action',
							params: {
								"order.orderId": record.get('orderId'),
						        "order.state": '已关闭',
						        "order.closeTime": new Date(),
						        "order.total": record.get('total'),
						        "order.userId": record.get('userId')
						    },
							success: function(response) {
								Ext.Msg.alert('提示', "订单关闭成功！");
								orderStore.reload();
							},
							failure: function(request) {
								Ext.MessageBox.show({
									title: '操作提示',
									msg: "连接服务器失败",
									buttons: Ext.MessageBox.OK,
									icon: Ext.MessageBox.ERROR
								});
							},
							method: 'post'
						});
					} else {
						Ext.Msg.alert("提示", "请选择订单！");
					}
				}
            }, {
             	xtype: 'button',
                text: '导出',
                iconCls: 'icon-plugin',
                handler: function() {
                	var order_export_window =  new Ext.Window({
             			resizable : false,
             				modal : true,
             				title : '请选择导出日期',
             				width: 300,
             				items: [ 
             				         new Ext.FormPanel({
             				        	url: 'orderExport.action',
             				        	defaults: {
             				        		anchor: '100%',
             				        	},
             				        	standardSubmit: true,
             				        	method: 'POST',
             				        	layout: 'anchor',
             				        	defaultType: 'textfield',
             				        	items: [{
             				        		xtype: 'datefield',
             				        		name: 'export_date',
             				        		allowBlank: false
             				        	}],
             				        	buttons: [{
             								text: '导出',
             								handler: function(btn) {
             									var frm = this.up('form').getForm();
             									if (frm.isValid()) {
             										frm.submit();
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
             									order_export_window.close();
             								}
             							}]
             				        })
             				]
             			}).show();
                }
            }
		]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: orderStore,
		columns: [
			{ text: '编号', dataIndex: 'orderId', flex: 1 },				
			{ text: '用户编号', dataIndex: 'userId', flex: 1 },
			{ text: '合计', dataIndex: 'total', flex: 1 },
			{ text: '提交日期', dataIndex: 'submitTime', flex: 2 },
			{ text: '结算日期', dataIndex: 'closeTime', flex: 2 },
			{ text: '订单状态', dataIndex: 'state', flex: 1 },
			{ text: '备注', dataIndex: 'note', flex: 2 }
		],
		dockedItems: [toolbarOrderInfo, {
	        xtype: 'pagingtoolbar',
	        store: orderStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }],
	    listeners: {
	    	itemclick: function ( view, record, item, index, e, eOpts ){
	    		if (record.get('state') != '已提交'){
	    			Ext.getCmp('btn-deliver').disable();
	    			Ext.getCmp('btn-close').disable();
	    		} else {
	    			Ext.getCmp('btn-deliver').enable();
	    			Ext.getCmp('btn-close').enable();
	    		}
	    	},
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
		    					        displayInfo: true,
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

var discountInfo = function(tabId, tabText){
	var panel = Ext.create('Ext.panel.Panel', {
		layout: 'column',
		bodyPadding: 5,
	    items: [{
	        xtype: 'textfield',
	        id: 'discountSearchText',
            emptyText : '请输入用户编号'
	    }, {
	        xtype: 'button',
	        text: '查询',
	        iconCls: 'icon-search',
	        handler: function(){
	        	discountSearchText = Ext.getCmp('discountSearchText').getValue();
	        	if (/\d+/.test(Ext.getCmp('discountSearchText').getValue()) == false){
					Ext.Msg.alert('提示', '请输入用户编号!');
	        		return;
	        	}
	        	var discountSearchStore = Ext.create('Ext.data.Store', {
	        		autoLoad: true,
	        		fields:['discountId', 'userId', 'channel', 'discount'],
	        		proxy: {
	        			type: 'ajax',
	        			url: 'findAllDiscountByUser.action',
	        			reader: {
	        				type: 'json',
	        				root: 'root',
	        				totalProperty: 'totalProperty'
	        			},
	        			extraParams: {
	        				searchType: 'userId',
	        				userId: Ext.getCmp('discountSearchText').getValue().trim()
	        			}
	        		}, 
        			listeners: {
        				'load': function(store) {
//        					alert(3)
        					uId = Ext.getCmp('discountSearchText').getValue().trim();
        					old = Ext.getCmp("discount-"+uId);
        					if (old) {
        						old.close();
        					}
//        					alert(4)
        					var toolbarDiscount = Ext.create('Ext.toolbar.Toolbar', {
        		        		items: [
        		        			{
        		        				text: '添加折扣',
        		        				iconCls: 'icon-add',
        		        				handler : function(){
        		        					var window = Ext.create('Ext.window.Window', {
        		        						title: '添加折扣',
        		        						layout: 'fit',
        		        						modal: true
        		        					}).show();
        		        								
        		        					var formPanel = Ext.create('Ext.form.Panel', {
        		        						bodyPadding: 5,
        		        						width: 500,
        		        						// The form will submit an AJAX request to this URL when submitted
        		        						url: 'saveDiscount.action',
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
        		        							name: 'discount.userId',
        		        						},{
        		        							xtype: "combo",
        		        							fieldLabel: "渠道",
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
        		        							name: 'discount.channel',
        		        							allowBlank: false
        		        						},{
        		        							fieldLabel: "折扣",
        		        							value: 1,
        		        							name: 'discount.discount',
        		        							xtype: 'numberfield'
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
        		        										   Ext.Msg.alert('提示', "添加折扣成功！");
        		        										   window.close();
        		        										   discountSearchStore.reload();
        		        										},
        		        										failure: function(form, action) {
        		        											Ext.Msg.alert('提示', action.result.tip);
        		        										}
        		        									});
        		        								}
        		        							}
        		        						}],
        		        					});
        		        					window.add(formPanel);
        		        				}
        		        			}, '-', {
        		        				xtype: 'textfield',
        		                        emptyText : '输入渠道查询',
        		                        id: 'channel_search_text' + uId
        		                    }, {
        		                        xtype: 'button',
        		                        text: '查询',
        		                        iconCls : 'icon-search',
        		                        handler: function() {
        		        					discountSearchStore.load({params : 
        		        						{ conditions: Ext.getCmp('channel_search_text'+uId).getValue().trim() }
        		        					});
        		        				}
        		                    }, '-', , {
        		        				text : '删除折扣',
        		        				iconCls : 'icon-del',
        		        				handler : function() {
        		        					var records = gridPanel.getSelectionModel().getSelection();
        		        					if (records && records.length > 0) {
        		        						Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
        		        							if (btn == 'yes') {
        		        								var ids = "";
        		        								for (var i = 0; i < records.length; i++){
        		        									ids += records[i].get("discountId");
        		        									if (i != records.length - 1){
        		        										ids += ",";
        		        									}
        		        								}
        		        								Ext.Ajax.request({
        		        									url : 'deleteDiscount.action',
        		        									params : {
        		        										ids: ids
        		        									},
        		        									success : function() {
        		        										Ext.Msg.alert('提示', "删除折扣成功！");
        		        										discountSearchStore.reload();
        		        									},
        		        									failure : function() {
        		        										Ext.Msg.show({
        		        											title : '提示',
        		        											msg : '删除时发生错误!',
        		        											buttons : Ext.Msg.OK,
        		        											icon : Ext.Msg.ERROR
        		        										});
        		        									}
        		        								});
        		        							}
        		        						});
        		        					} else {
        		        						Ext.Msg.alert("提示", "请选择要删除的项!");
        		        					}
        		        				}
        		        			}
        		        		]
        		        	});
//        					alert(2)
    		        		var gridPanel = Ext.create('Ext.grid.Panel', {
    		        			id: "discountpanel" + uId,
    		        			store: discountSearchStore,
    		        			columns: [
									{ text: '折扣编号', dataIndex: 'discountId', flex: 1 },
									{ text: '用户ID', dataIndex: 'userId', flex: 1 },
									{ text: '渠道', dataIndex: 'channel', flex: 1 },
									{ text: '折扣', dataIndex: 'discount', editor: {
						                xtype: 'numberfield',
						                allowBlank: false
						            }, flex: 1 }
    		        			],
    		        			selModel: Ext.create('Ext.selection.CheckboxModel',{mode: "SIMPLE"}),
    		        			selType: 'cellmodel',
    		        		    plugins: [
    		        		          	Ext.create('Ext.grid.plugin.CellEditing', {
    		        		              	clicksToEdit: 1
    		        		          	})
    		        		    ],
    		        			dockedItems: [toolbarDiscount, {
    						        xtype: 'pagingtoolbar',
    						        store: discountSearchStore,   // same store GridPanel is using
    						        dock: 'bottom',
    						        displayInfo: true
    						    }],
    						    listeners: {
    						    	'edit': function(editor, e){
    						    		var field = "discount." + e.field;
    						    		var p = {};
    						    		p[field] = e.value;
    						    		p["discount.discountId"] = e.record.get("discountId");
    						    	    Ext.Ajax.request({
    						    			url : 'updateDiscount.action',
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
    						    	}
    						    }
    		        		});
//    		        		alert(1)
    		        		createTab("discount-" + uId, "用户折扣-" + uId, gridPanel);
        				}
        			},
	        	});
	        }
	    }]
	});
	createTab(tabId, tabText, panel);
};

var yunfeiOrders = function(tabId, tabText){
	var yunfeiOrdersStore = Ext.create('Ext.data.Store', {
		storeId:'yunfeiStore',
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
	
	var toolbarYunfeiInfo = Ext.create('Ext.toolbar.Toolbar', {
		items: [
			{
				text: '添加运费信息',
				iconCls : 'icon-add',
				handler : function(){
					var window = Ext.create('Ext.window.Window', {
						title: '运费信息',
						layout: 'fit',
						modal: true
					}).show();
								
					var formPanel = Ext.create('Ext.form.Panel', {
						bodyPadding: 5,
						width: 500,
						// The form will submit an AJAX request to this URL when submitted
						url: 'saveFreight.action',
						// Fields will be arranged vertically, stretched to full width
						layout: 'anchor',
						defaults: {
							anchor: '100%'
						},
			
						// The fields
						defaultType: 'textfield',
						items: [{
							fieldLabel: '省份',
							name: 'freight.priovice',
							allowBlank: false
						},{
							xtype: 'numberfield',
							fieldLabel: '首重',
							name: 'freight.firstfreight',
							allowBlank: false
						},{
							xtype: 'numberfield',
							fieldLabel: '续重',
							name: 'freight.lastfreight',
							allowBlank: false
						},{
							fieldLabel: '快递公司',
							name: 'freight.freightcompany',
							allowBlank: false
						},{
							fieldLabel: '渠道名称',
							name: 'freight.channel',
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
									//	var json = Ext.util.JSON.decode(resp.responseText);
										   Ext.Msg.alert('Success', action.result.msg);
										   window.close();
										   Ext.data.StoreManager.lookup('yunfeiStore').reload();
										},
										failure: function(form, action) {
											Ext.Msg.alert('Failed', action.result.msg);
										}
									});
								}
							}
						}],
					});
					window.add(formPanel);
				}
			},
			{
				text : '编辑运费管理',
				iconCls : 'icon-edit',
				handler : function(){
							var record = gridPanel.getSelectionModel().getSelection()[0];
							if(record){
								var window = Ext.create('Ext.window.Window', {
									title: '编辑用户',
									layout: 'fit',
									modal: true
								}).show();
											
								var formPanel = Ext.create('Ext.form.Panel', {
									bodyPadding: 5,
									width: 500,
									// The form will submit an AJAX request to this URL when submitted
									url: 'updateFreight.action',
									// Fields will be arranged vertically, stretched to full width
									layout: 'anchor',
									defaults: {
										anchor: '100%'
									},
						
									// The fields
									defaultType: 'textfield',
									items: [{
										xtype: "hiddenfield",
										value: record.getData().freightid,
										name: 'freight.freightid',
										allowBlank: false
									},{
										fieldLabel: '省份',
										value: record.getData().priovice,
										name: 'freight.priovice',
										allowBlank: false
										
									},{
										fieldLabel: '首重',
										value: record.getData().firstfreight,
										name: 'freight.firstfreight',
										allowBlank: false
										
									},{
										fieldLabel: '续重',
										name: 'freight.lastfreight',
										value: record.getData().lastfreight,
										allowBlank: false
									},{
										fieldLabel: '快递公司',
										name: 'freight.freightcompany',
										value: record.getData().freightcompany,
										allowBlank: false
									},{
										fieldLabel: '渠道名称',
										name: 'freight.channel',
										value: record.getData().channel,
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
													   yunfeiOrdersStore.reload();
													},
													failure: function(form, action) {
														Ext.Msg.alert('Failed', action.result.msg);
													}
												});
											}
										}
									}],
								});
								window.add(formPanel);
							}
						}
			},
			{
				text : '删除运费',
				iconCls : 'icon-del',
				handler : function() {
					var record = gridPanel.getSelectionModel().getSelection();
			//		alert(record[0].getData().freightid);
					if (record) {
						Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : 'deleteFreight.action',
									params : {
										freightid : record[0].getData().freightid
									},
									success : function() {
										Ext.Msg.show({
											title : '成功提示',
											msg : '删除成功!'
										});
										yunfeiOrdersStore.reload();
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
                id: 'yunfei_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls : 'icon-search',
                handler: function() {
					// Ext.data.StoreManager.lookup('yunfeiStore').baseParams.conditions = user_search_book.getValue();
					//alert(yunfei_search_text.getValue());
					
					Ext.data.StoreManager.lookup('yunfeiStore').load({params : { conditions: Ext.getCmp('yunfei_search_text').getValue() } });
				}
            },'-',
            {
                xtype: 'button',
                text: '运费模板导出',
                iconCls : 'icon-plugin',
                handler: function() {
	            	new Ext.Window({
						closeAction : 'close',
						resizable : false,
						bodyStyle : 'padding: 7',
						modal : true,
						title : '请导出',
						html : 
							 	'<a href="exportExcel.action">导出到Excel模版</a>'
								,
						width : 300,
						height : 100
					}).show();
				}
            }
            
            ,
            {
           	 xtype: 'button',
                text: '导入',
                iconCls: 'icon-plugin',
                handler: function() {
               	 var import_window =  new Ext.Window({
            			resizable : false,
            				modal : true,
            				title : '请选择导入文件',
            				items: [ 
            				         new Ext.FormPanel({
            				        	url: 'upload2.action',
            				        	defaults: {
            				        		anchor: '100%',
            				        	},
            				        	layout: 'anchor',
            				        	defaultType: 'textfield',
            				        	items: [{
            				        		xtype: 'fileuploadfield',
            				        		name: 'excelFile',
            				        		allowBlank: false
            				        	}],
            				        	buttons: [{
            								text: '导入',
            								handler: function(btn) {
            									var frm = this.up('form').getForm();
            									if (frm.isValid()) {
            										frm.submit({
            											waitTitle: '请稍候',
            											waitMsg: '正在提交表单数据,请稍候...',
            											success: function(form, action) {
            												Ext.Msg.alert( '提示', "导入成功");
            												import_window.close();
            												yunfeiOrdersStore.reload();
            											},
            											failure: function() {
            												Ext.Msg.show({
            													title: '错误提示',
            													msg: '该商品可能已经存在!',
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
            									import_window.close();
            								}
            							}]
            				        })
            				]
            			}).show();
                }
           }
		]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: yunfeiOrdersStore,
		columns: [
			{ text: '省份', dataIndex: 'priovice', flex: 3},
			{ text: '首重', dataIndex: 'firstfreight', flex: 3 },
			{ text: '续重', dataIndex: 'lastfreight', flex: 3 },
			{ text: '快递公司', dataIndex: 'freightcompany', flex: 3 },
			{ text: '渠道名称', dataIndex: 'channel', flex: 3 }
		
		],
		dockedItems: [toolbarYunfeiInfo,{
	        xtype: 'pagingtoolbar',
	        store: Ext.data.StoreManager.lookup('yunfeiStore'),   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true,
	    }]
	});
	createTab(tabId, tabText, gridPanel);
};
