var userInfo = function(tabId, tabText){
	Ext.create('Ext.data.Store', {
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
				iconCls : 'icon-add',
				handler : function(){
					var window = Ext.create('Ext.window.Window', {
						title: '提现信息',
						layout: 'fit',
						modal: true
					}).show();
								
					var formPanel = Ext.create('Ext.form.Panel', {
						bodyPadding: 5,
						width: 500,
						// The form will submit an AJAX request to this URL when submitted
						url: 'saveUser.action',
						// Fields will be arranged vertically, stretched to full width
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
							formBind: true, //only enabled once the form is valid
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
						}]
					});
					window.add(formPanel);
				}
			},
			{
				text : '编辑用户',
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
									url: 'updateUser.action',
									// Fields will be arranged vertically, stretched to full width
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
										formBind: true, //only enabled once the form is valid
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
									}]
								});
								window.add(formPanel);
							}
						}
			},
			{
				text : '删除用户',
				iconCls : 'icon-del',
				handler : function() {
					var record = gridPanel.getSelectionModel().getSelection();
					if (record) {
						Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : 'deleteUser.action',
									params : {
										userId : record[0].getData().userId
									},
									success : function() {
										Ext.data.StoreManager.lookup('userStore').reload();
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
                id: 'user_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls : 'icon-search',
                handler: function() {
					// Ext.data.StoreManager.lookup('userStore').baseParams.conditions = user_search_book.getValue();
					//alert(user_search_text.getValue());
					
					Ext.data.StoreManager.lookup('userStore').load({params : { conditions: Ext.getCmp('user_search_text').getValue() } });
				}
            }
		]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: Ext.data.StoreManager.lookup('userStore'),
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
	        store: Ext.data.StoreManager.lookup('userStore'),   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }]
	});
	createTab(tabId, tabText, gridPanel, toolbarUserInfo);
};

var rechargeInfo = function(tabId, tabText){
	var rechargeStore = Ext.create('Ext.data.Store', {
		storeId:'rechargeStore',
		fields:['rechargeId', 'userId', 'method', 'accountName', 'amount', 'tbOrderId', 'submitTime', 'closeTime', 'state', 'note'],
		proxy: {
			type: 'ajax',
			url: 'findAllRecharge.action',
			reader: {
				type: 'json',
				root: 'root'
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
                iconCls : 'icon-search',
                handler: function() {
					rechargeStore.load({params : { from: Ext.getCmp('manage_recharge_search_time_from').getRawValue(), to: Ext.getCmp('manage_recharge_search_time_to').getRawValue(), userId: userId } });
				}
            }, {
				xtype: 'textfield',
                emptyText : '多条件可用逗号或者空格隔开!',
                id: 'manage_recharge_search_text'
            }, {
                xtype: 'button',
                text: '查询',
                iconCls : 'icon-search',
                handler: function() {
					rechargeStore.load({params : { conditions: Ext.getCmp('manage_recharge_search_text').getValue() } });
				}
            }, {
             	xtype: 'button',
                text: '通过',
                iconCls : 'icon-add',
                handler: function() {
					var record = gridPanel.getSelectionModel().getSelection()[0];
					if (record && record.getData().state == '已提交') {
						Ext.Ajax.request({
							url : 'updateRecharge.action',
							params: {
						        "recharge.rechargeId": record.getData().rechargeId,
						        "recharge.state": '已通过',
						        "recharge.closeTime": new Date()
						    },
							success : function(response) {
								Ext.Msg.alert('Success', "充值记录更新成功！");
								rechargeStore.reload();
							},
							failure : function(request) {
								Ext.MessageBox.show({
									title : '操作提示',
									msg : "连接服务器失败",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
							},
							method : 'post'
						});
					}
				}
            }, {
             	xtype: 'button',
                text: '拒绝',
                iconCls : 'icon-del',
                handler: function() {
					var record = gridPanel.getSelectionModel().getSelection()[0];
					if (record && record.getData().state == '已提交'){
						Ext.Ajax.request({
							url : 'updateRecharge.action',
							params: {
						        "recharge.rechargeId": record.getData().rechargeId,
						        "recharge.state": '已拒绝',
						        "recharge.closeTime": new Date()
						    },
							success : function(response) {
								Ext.Msg.alert('Success', "充值记录更新成功！");
								rechargeStore.reload();
							},
							failure : function(request) {
								Ext.MessageBox.show({
									title : '操作提示',
									msg : "连接服务器失败",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
							},
							method : 'post'
						});
					}
				}
            }
		]
	});
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: rechargeStore.load({ params: {} }),
		columns: [
			{ text: '编号', dataIndex: 'rechargeId'},				
			{ text: '用户编号', dataIndex: 'userId'},
			{ text: '充值方式', dataIndex: 'method'},
			{ text: '账户名称', dataIndex: 'accountName'},
			{ text: '充值金额', dataIndex: 'amount'},
			{ text: '淘宝订单编号', dataIndex: 'tbOrderId' },
			{ text: '提交日期', dataIndex: 'submitTime' },
			{ text: '结算日期', dataIndex: 'closeTime' },
			{ text: '充值状态', dataIndex: 'state' },
			{ text: '备注', dataIndex: 'note' }
		],
		dockedItems: [toolbarRechargeInfo, {
	        xtype: 'pagingtoolbar',
	        store: rechargeStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }]
	});
	
	createTab(tabId, tabText, gridPanel);
};