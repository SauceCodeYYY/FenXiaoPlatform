// 商品管理
var Subject = Ext.data.Record.create([
   {name : 'subjectId',mapping : 'subjectId',type : 'int'},
    {name : 'novid',mapping : 'novid',type : 'string'}, 
	{name : 'brand',mapping : 'brand',type : 'string'}, 
	{name : 'sizeone',mapping : 'sizeone',type : 'string'}, 
	{name : 'sizetwo',mapping : 'sizetwo',type : 'string'}, 
	{name : 'largeclass',mapping : 'largeclass',type : 'string'}, 
	{name : 'styles',	mapping : 'styles',type : 'string'}, 
	{name : 'categoryId',mapping : 'categoryId',type : 'string'}, 
	{name : 'color',	mapping : 'color',type : 'string'}, 
	{name : 'object',mapping : 'object',type : 'string'}, 
	{name : 'subjectName',mapping : 'subjectName',type : 'string'}, 
	{name : 'tagprice',	mapping : 'tagprice',type : 'string'}, 
	{name : 'discount',mapping : 'discount',type : 'string'}, 
	{name : 'season',	mapping : 'season',type : 'string'}, 
	{name : 'series',	mapping : 'series',type : 'string'}, 
	{name : 'sex',mapping : 'sex',type : 'string'}, 
	{name : 'year',mapping : 'year',	type : 'string'}, 
	{name : 'remarks',mapping : 'remarks',type : 'string'}, 
	{name : 'province',mapping : 'province',type : 'string'}, 
	{name : 'total',mapping : 'total',type : 'string'}
   
   
   
   ]);

var cm_subject = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
{header : '货号',width : 80,dataIndex : 'novid',menuDisabled : true,	sortable : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})},
{header : '货号名称',width : 80,dataIndex : 'subjectName',menuDisabled : true,	sortable : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})}, 
{header : '品牌',width : 80,	dataIndex : 'brand',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})}, 
{header : '尺码1',width : 80,	dataIndex : 'sizeone',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})}, 
{header : '尺码2',width : 80,	sortable : true,dataIndex : 'sizetwo',menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})}, 
{header : '大类',width : 80,	dataIndex : 'largeclass',id : 'largeclass',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})}, 
{header : '款型',width : 80,	dataIndex : 'styles',id : 'styles',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})}, 
{header : '颜色',width : 80,	dataIndex : 'color',id : 'color',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})},
{header : '项目',width : 80,	dataIndex : 'object',id : 'object',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})},
{header : '吊牌价',width : 80,	dataIndex : 'tagprice',id : 'tagprice',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})},
{header : '季节',width : 80,	dataIndex : 'season',id : 'season',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})},
{header : '性别',width : 80,	dataIndex : 'sex',id : 'sex',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})},
{header : '年份',width : 80,	dataIndex : 'year',id : 'year',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})},
{header : '折扣',width : 80,	dataIndex : 'discount',id : 'discount',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})},
{header : '总计',width : 80,	dataIndex : 'total',id : 'discount',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 20})},
{header : '备注',width :100,dataIndex : 'remarks',id : 'remarks',sortable : true,menuDisabled : true,editor : new Ext.form.TextField({allowBlank : false,maxLength : 50})}
//{header : '',id : 'remark',width:80,dataIndex : 'remark',menuDisabled : true,editor : new Ext.form.TextField({maxLength : 0})}
 ]);

cm_subject.defaultSortable = false;

var window_add_subject = new Ext.Window({
	title : '添加商品',
	width : 700,
	//height : 440,
	resizable : false,
	autoHeight : true,
	modal : true,
	closeAction : 'hide',
	constrainHeader:true,
	listeners : {
		'hide' : function() {
			this.setTitle('添加货品名称');
			this.findById('subject.subjectName').ownerCt.form.reset();
		}
	},
	items : [new Ext.FormPanel({
		labelWidth : 70,
		labelAlign : 'right',
		url : 'saveSubject.action',
		border : false,
		baseCls : 'x-plain',
		bodyStyle : 'padding:5px 5px 0',
	//	anchor : '100%',
	//	fileUpload : true,
	    anchor : '100%',
		defaults : {
			//width : 233,
			msgTarget : 'side' // 验证信息显示右边
		},
    //	defaultType : 'textfield',
		items : [
		  {
			layout : 'column',
			border : false,
			baseCls : 'x-plain',
			items : [
			    {
					columnWidth : .5,
					layout : 'form',
					baseCls : 'x-plain',
					border : false,
					defaultType : 'textfield',
					defaults : {anchor : '93%'},
				items : 
				[
					{fieldLabel : '货号',	name : 'subject.novid',allowBlank : false,maxLength : 50},        
					{fieldLabel : '货品名称',id : 'subject.subjectName',name : 'subject.subjectName',maxLength : 25}, 
					{fieldLabel : '品牌',name : 'subject.brand',maxLength : 50}, 
					{fieldLabel : '尺寸1',name : 'subject.sizeone',	maxLength : 50}, 
					{fieldLabel : '尺寸2',	name : 'subject.sizetwo',maxLength : 25}, 
					{fieldLabel : '大类',name : 'subject.largeclass',maxLength : 25}, 
					{fieldLabel : '款型',name : 'subject.styles',	maxLength : 25},
					{fieldLabel : '年份',name : 'subject.year',maxLength : 25}
				]
			   }, 
			   {
					columnWidth : .5,
					layout : 'form',
					border : false,
					baseCls : 'x-plain',
					defaultType : 'textfield',
					defaults : {anchor : '93%'},
				items : [
					{fieldLabel : '颜色',name : 'subject.color',maxLength : 50}, 
					{fieldLabel : '项目',name : 'subject.object',maxLength : 50}, 
					{fieldLabel : '吊牌价',name : 'subject.tagprice',maxLength : 25}, 
					{fieldLabel : '折扣',name : 'subject.discount',maxLength : 25}, 
					{fieldLabel : '性别',name : 'subject.sex',maxLength : 25}, 
					{fieldLabel : '系列',name : 'subject.series',maxLength : 25}, 
					{fieldLabel : '季节',name : 'subject.season',maxLength : 25},
					{fieldLabel : '省份',name : 'subject.province',maxLength : 25},
					{fieldLabel : '总计',name : 'subject.total',maxLength : 25}
					]
			}]
		}, 
		{
			xtype : 'tabpanel',
			plain : true,
			bodyBorder : false,
			activeTab : 0,
			height : 100,
			defaults : {bodyStyle : 'padding:2px'},
			items : [
              {title : '备注',layout : 'fit',items : {xtype : 'textarea',name : 'subject.remarks',maxLength : 500}}
			]
		}],
		buttonAlign : 'center',
		minButtonWidth : 60,
		buttons : [{
			text : '添加',
			handler : function(btn) {
				var frm = this.ownerCt.form;
				if (frm.isValid()) {
					btn.disable();
					var cnfield = frm.findField('subject.subjectName');
					frm.submit({
						waitTitle : '请稍候',
						waitMsg : '正在提交表单数据,请稍候...',
						success : function(form, action) {
							var store = grid_subject.getStore();
							var subject = new Subject({
								subjectId : action.result.subjectId,
								subjectName : cnfield.getValue(),
								novid : form.findField('subject.novid').getValue(),
								brand : form.findField('subject.brand').getValue(),
								sizeone : form.findField('subject.sizeone').getValue(),
								sizetwo : form.findField('subject.sizetwo').getValue(),
								largeclass : form.findField('subject.largeclass').getValue(),
								styles : form.findField('subject.styles').getValue(),
								color : form.findField('subject.color').getValue(),
								object: form.findField('subject.object').getValue(),
								tagprice : form.findField('subject.tagprice').getValue(),
								discount : form.findField('subject.discount').getValue(),
								season : form.findField('subject.season').getValue(),
								series : form.findField('subject.series').getValue(),
								sex : form.findField('subject.sex').getValue(),
								year : form.findField('subject.year').getValue(),
								remarks : form.findField('subject.remarks').getValue()
							
							});
							store.insert(0, [subject]);
							window_add_subject.setTitle('[ ' + cnfield.getValue() + ' ]   添加成功!!');
							cnfield.reset();
							btn.enable();
						},
						failure : function() {
							Ext.Msg.show({
								title : '错误提示',
								msg : '"' + cnfield.getValue() + '" ' + '名称可能已经存在!',
								buttons : Ext.Msg.OK,
								fn : function() {
									cnfield.focus(true);
									btn.enable();
								},
								icon : Ext.Msg.ERROR
							});
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				this.ownerCt.form.reset();
			}
		}, {
			text : '取消',
			handler : function() {
				this.ownerCt.ownerCt.hide();
			}
		}]
	})]
});

var btn_add_subject = new Ext.Button({
	text : '添加商品',
	iconCls : 'icon-add',
	handler : function() {
		window_add_subject.show();
	}
});

var btn_del_subject = new Ext.Button({
	text : '删除商品',
	iconCls : 'icon-del',
	handler : function() {
		var record = grid_subject.getSelectionModel().getSelected();
		if (record) {
			Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
						url : 'deleteSubject.action',
						params : {
							subjectId : record.data.subjectId
						},
						success : function() {
							grid_subject.getStore().remove(record);
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
});

var btn_search_subject = new Ext.Button({
	text : '查询',
	iconCls : 'icon-search',
	handler : function() {
		ds_subject.load();
	}
});

var btn_update_subject = new Ext.Button({
text : '修改：<font color=red>点击查询，在当前行选择修改，红色标记为修改后状态</font>',
iconCls : 'icon-edit',
handler : function() {

}
});

var ds_subject = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
		url : 'findAllSubject.action'
	}),
	reader : new Ext.data.JsonReader({
		root : 'root'
	}, [{
		name : 'subjectId',
		type : 'int'
	}, {
		name : 'subjectName',
		type : 'string'
	}, 
	{
		name : 'novid',
		type : 'string'
	},
	{
		name : 'brand',
		type : 'string'
	},
	{
		name : 'sizeone',
		type : 'string'
	},
	{
		name : 'sizetwo',
		type : 'string'
	},
	{
		name : 'largeclass',
		type : 'string'
	},
	{
		name : 'styles',
		type : 'string'
	},
	{
		name : 'color',
		type : 'string'
	},
	{
		name : 'object',
		type : 'string'
	},
	{
		name : 'tagprice',
		type : 'string'
	},
	{
		name : 'discount',
		type : 'string'
	},
	{
		name : 'season',
		type : 'string'
	},
	{
		name : 'sex',
		type : 'string'
	},{
		name : 'year',
		type : 'string'
	},
	{
		name : 'remarks',
		type : 'string'
	},
	{
		name : 'province',
		type : 'string'
	},
	{
		name : 'total',
		type : 'string'
	}])
});

var grid_subject = new Ext.grid.EditorGridPanel({
	title : '商品管理',
	iconCls : 'icon-grid',
	loadMask : {
		msg : '数据加载中...'
	},
	region : 'center',
	cm : cm_subject,
	ds : ds_subject,
	sm : new Ext.grid.RowSelectionModel({
		singleSelect : true
	}),
	enableColumnMove : false,
	trackMouseOver : false,
	frame : true,
	
	autoExpandColumn : 'remarks',
	clicksToEdit : 1,
	tbar : [btn_add_subject, '-', btn_del_subject, '-', btn_search_subject, '-', btn_update_subject],
	listeners : {
		'afteredit' : function(e) {
			Ext.Ajax.request({
				url : 'updateSubject.action',
				params : {
					fieldName : e.field,
					fieldValue : e.value,
					subjectId : e.record.data.subjectId
				},
				failure : function() {
					Ext.Msg.show({
						title : '错误提示',
						msg : '修改数据发生错误,操作将被回滚!',
						fn : function() {
							e.record.set(e.field, e.originalValue);
						},
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.ERROR
					});
				}
			});
		}
	}
});



var p_subject = {
	id : 'subject-panel',
	border : false,
	layout : 'border',
	items : [grid_subject]
};