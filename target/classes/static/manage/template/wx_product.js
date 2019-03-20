/**
 * 班级管理模型
 */
(function(global){
	
        global.modelName = "wx_product";

        global.model = {
            desc : "活动管理",
            key : "id",
            columns :[
                {
                    name : "id",
                    desc : "活动编号",
                    type : 0,
                    needInput : true,
                    initData :[],
                    refs:{},
                    value : 0
                },
				{
                    name : "company",
                    desc : "公司",
                    type : 1,
                    needInput : true,
                    initData :[],
                    refs:{},
                    value : '0'
                },
                {
                    name : "name",
                    desc : "活动标题",
                    type : 1,
                    needInput : true,
                    initData :[],
                    refs:{},
                    value : '0'
                },
                {
                    name : "main_image",
                    desc : "活动照片",
                    type : 6,
                    needInput : true,
                    initData :[],
                    refs:{},
                    value : ""
                },
				{
                    name : "detail",
                    desc : "活动详情",
                    type : 10,
                    needInput : true,
                    initData :[],
                    refs:{},
                    value : '0'
                },
                {
                    name : "price",
                    desc : "价格",
                    type : 1,
                    needInput : true,
                    initData :[],
                    refs:{},
                    value : 0
                },
                {
                    name : "channel_price",
                    desc : "渠道价格",
                    type : 1,
                    needInput : true,
                    initData :[],
                    refs:{},
                    value : 0
                },
				{
                    name : "stock",
                    desc : "库存数量",
                    type : 1,
                    needInput : true,
                    initData :[],
                    refs:{},
                    value : 0
                },
				{
                    name : "limit_stock",
                    desc : "起购数量",
                    type : 1,
                    needInput : true,
                    initData :[],
                    refs:{},
                    value : 0
                }
            ]
        };
		
		global.optionalModel = undefined;

})(this);