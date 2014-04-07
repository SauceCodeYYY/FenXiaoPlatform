var selfInfo = function(tabId, tabText){
	var currentUserStore = Ext.create('Ext.data.Store', {
		storeId:'currentUserStore',
		autoLoad: true,
		fields:['userId', 'userName', 'email', 'phone', 'role', 'points', 'wwId', 'balance'],
		proxy: {
			type: 'ajax',
			url: 'findUserByExample.action?user.userId=' + userId,
			reader: {
				type: 'json',
				root: 'root',
				totalProperty: 'totalProperty'
			}
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
		
					// The fields
					defaultType: 'textfield',
					items: [{
						fieldLabel: '用户ID',
						name: 'user.userId',
						allowBlank: false,
						value: currentUser.data.userId,
						readOnly: true
					},{
						fieldLabel: '昵称',
						name: 'user.userName',
						allowBlank: false,
						value: currentUser.data.userName,
						readOnly: true
					},{
						fieldLabel: '邮箱',
						name: 'user.email',
						value: currentUser.data.email,
						allowBlank: false
					},{
						fieldLabel: '联系方式',
						name: 'user.phone',
						value: currentUser.data.phone,
						allowBlank: false
					},{
						fieldLabel: '账户余额',
						name: 'user.balance',
						value: currentUser.data.balance,
						readOnly: true
					},{
						fieldLabel: '积分',
						name: 'user.points',
						value: currentUser.data.points,
						readOnly: true
					},{
						fieldLabel: '角色',
						name: 'user.role',
						allowBlank: false,
						value: currentUser.data.role,
						readOnly: true
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
									},
									failure: function(form, action) {
										Ext.Msg.alert('Failed', action.result.msg);
									}
								});
							}
						}
					}],
				});
				createTab(tabId, tabText, formPanel);
		    }
		}
	});
};

var selfRecharge = function(tabId, tabText){
	var selfRechargeStore = Ext.create('Ext.data.Store', {
		storeId:'selfRechargeStore',
		fields:['rechargeId', 'userId', 'method', 'accountName', 'amount', 'tbOrderId', 'submitTime', 'closeTime', 'state', 'note'],
		proxy: {
			type: 'ajax',
			url: 'findAllRechargeByUser.action',
			reader: {
				type: 'json',
				root: 'root'
			}
		}
	});
	var gridPanel = Ext.create('Ext.grid.Panel', {
		store: selfRechargeStore.load({ params: { } }),
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
		dockedItems: [{
	        xtype: 'pagingtoolbar',
	        store: selfRechargeStore,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }],
	});
	
	var toolbar = Ext.create('Ext.toolbar.Toolbar', {
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
							allowBlank: false,
						},{
							fieldLabel: '充值金额',
							name: 'recharge.amount',
							allowBlank: false
						},{
							fieldLabel: '淘宝订单编号',
							name: 'recharge.tbOrderId',
							allowBlank: false,
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
							allowBlank: true,
						},{
							fieldLabel: '状态',
							name: 'recharge.state',
							value: '已提交',
							readOnly: true
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
										   selfRechargeStore.reload();
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
	
	createTab(tabId, tabText, gridPanel, toolbar);
};
