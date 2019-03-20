/**
 * 界面控制类
 */
(function(global){
    var teachingTime = undefined;
    var student = undefined;

    function getIndexForMeta(name) {
        return window.vm.getColumnMetaByName(name).columnIndex;
    }

    function setValueForMeta(index, prop, data) {
        window.vm.tableMeta.columns[index][prop] = data;
    }

    function getValueForMeta(index, prop) {
        return window.vm.tableMeta.columns[index][prop];
    }

    function getTranslateByName(prop) {
        return window.vm.columnTranslate[prop].translate;
    }
    
    function setTranslateByName(prop,  data){
        window.vm.columnTranslate[prop] = {translate : data};
    }
    
    // 是否是多选字段 -- 暂时先按字段写,多个写成或
    function isMultiColumn(columnName){
        return columnName == "absentStudentId";
    }
    
    // 转换数据库字段到js
    function translateDataBase2Js(metaColumn, value){
        //var result = metaColumn.data.type == 5 ? (value == "" ? "" : new Date(value)) : value;
		var result = value;
        return !isMultiColumn(metaColumn.data.name) ? result :
			( result == undefined || result.length == 0 ? [] : result.split(",").map(function(oneValue){
				return parseInt(oneValue);}));
    }
    
    // 表头格式化
    function formatterColumn(nameDesc) {
        var result = nameDesc;
        var charArray = ["(", "（" ];
        for(var index = 0; index < charArray.length; index ++)
            if(result.indexOf(charArray[index]) >= 0)
                result = result.substring(0, result.indexOf(charArray[index]));
        return result;
    }
    
    // 转换数据的显示,主要是表格数据格式化
    function data2View(columnName, value) {
        var vm = window.vm;
        var translate = !!vm.columnTranslate[columnName] ? vm.columnTranslate[columnName].translate : [];
        // 如果字段没有翻译,直接返回
        if(translate.length == 0) return typeof value == 'number' ? value.toString() : value;
        // 在翻译结果中寻找值
        var isMulti = isMultiColumn(columnName);
        var result = "";
        for(var index = 0 ; index < translate.length; index++){
            var transValue = translate[index].value;
            var transDesc = !!translate[index].desc && !!translate[index].desc.length > 0 ?translate[index].desc : transValue;
            if(isMulti){
				if(value != undefined){
					var array = value.split(",");
					if(array.indexOf(transValue.toString()) >= 0)
						result += transDesc + ",";
				}
            }else if(value == transValue)
                return transDesc;
        }
        return result;
    }
    
    // 下来框关联初始化
    function loading_select_init(classId){
        var vm = window.vm;

        var lastClassNumberIndex = getIndexForMeta("lastClassNumber");
        var lastTeachingPeriodIndex = getIndexForMeta("lastTeachingPeriod");
        setValueForMeta(lastClassNumberIndex, "value", 0);
        setValueForMeta(lastTeachingPeriodIndex, "value", 0);
        setValueForMeta(getIndexForMeta("beLate"), "value", 21);
		setValueForMeta(getIndexForMeta("teachingType"), "value", 0);
		setValueForMeta(getIndexForMeta("teachingDate"), "value", new Date().format("yyyy-MM-dd"));

        for(var index = 0; index < vm.allLoadData.length; index ++){
            if(vm.allLoadData[index]["teachingType"] != 2 && vm.allLoadData[index]["classId"] == classId){
                setValueForMeta(lastClassNumberIndex, "value", vm.allLoadData[index]["currentClassNumber"]);
                setValueForMeta(lastTeachingPeriodIndex, "value", vm.allLoadData[index]["currentTeachingPeriod"]);
                break;
            }
        }
        setValueForMeta(getIndexForMeta("currentClassNumber"), "value", getValueForMeta(lastClassNumberIndex, "value") + 1);
		
		// 以下是特殊流程, 这里简单写死, 就是课程加 + 1 看看是否是测评  2018-04-14
		try{
			var lastTeachingPeriod = getValueForMeta(lastTeachingPeriodIndex, "value");
			if(!!lastTeachingPeriod && lastTeachingPeriod.indexOf("FP") == 0){
				lastTeachingPeriod = lastTeachingPeriod.split("&&")[0];
				var lastTeachingPeriodLeft = lastTeachingPeriod.substring(0, lastTeachingPeriod.length - 1);
				var lastTeachingPeriodRight = lastTeachingPeriod.substring(lastTeachingPeriod.length - 1, lastTeachingPeriod.length);
				if(!isNaN(lastTeachingPeriodRight)){
					var currentTeachingPeriod = lastTeachingPeriodLeft + (Number(lastTeachingPeriodRight) + 1);
					var arrayP = currentTeachingPeriod.split("-");
					var currentP = arrayP.length > 1 ? arrayP[1] : 0;
					if(checkResult["FP"]["data"].indexOf(currentP) >= 0){
						setValueForMeta(getIndexForMeta("currentTeachingPeriod"), "value", currentTeachingPeriod + "&&" + checkResult["FP"]["desc"]);
					}
				}
			}
		}catch(err){
			
		}
		// end 2018-04-14

        if(!!this.teachingTime){
            for(var index = 0; index < this.teachingTime.length; index ++){
                if(this.teachingTime[index]["classId"] == classId){
                    var teacherInit = [];
                    var translate = getTranslateByName("teacherId");
                    for(var teacherIndex = 0; teacherIndex < translate.length; teacherIndex ++)
                        if(translate[teacherIndex].value == this.teachingTime[index]["teacherId"])
                            teacherInit.push(translate[teacherIndex]);
                    setValueForMeta(getIndexForMeta("teacherId"), "initData", teacherInit);
					if(teacherInit.length > 0) setValueForMeta(getIndexForMeta("teacherId"), "value", teacherInit[0].value);
					

                    var teachingTime1 = this.teachingTime[index]["teachingTime1"];
                    var teachingTime2 = this.teachingTime[index]["teachingTime2"];
                    var teachingInit = !!teachingTime2 ? [{value : teachingTime1 , desc : teachingTime1},
                        {value : teachingTime2, desc :teachingTime2}] :[{value : teachingTime1 , desc : teachingTime1}];
                    setValueForMeta(getIndexForMeta("teachingTime"), "initData", teachingInit);
                    break;
                }
            }
        }
        
        if(!! this.student){
            var studentInit = [];
            for(var index = 0; index < this.student.length; index ++){
                if(this.student[index]["classId"] == classId){
                    var studentId = this.student[index]["studentId"];
                    var studentName = this.student[index]["chineseName"] + this.student[index]["englishName"];
                    studentInit.push({value : studentId, desc : studentName});
                }
            }
            var index = getIndexForMeta("absentStudentId");
            setValueForMeta(index, "value", []);
            setValueForMeta(index, "initData", studentInit);
        }
    }

    function bind_select_change(event){
        if(this.modelName == "teaching"){
            if(event.target.id == "classId"){
                var classId = event.target.value;
                loading_select_init(classId);
            }
        }

    }
	
	// 授课记录界面参数控制
	function setTeachingRadioControl(disabled, needInput){
		setValueForMeta(getIndexForMeta("teachingTime"), "disabled", disabled);
		setValueForMeta(getIndexForMeta("teachingAddress"), "disabled", disabled);
		setValueForMeta(getIndexForMeta("contactPhoneNumber"), "disabled", disabled);
		setValueForMeta(getIndexForMeta("currentClassNumber"), "disabled", disabled);
		setValueForMeta(getIndexForMeta("currentTeachingPeriod"), "disabled", disabled);
		setValueForMeta(getIndexForMeta("teacherArriveTime"), "disabled", disabled);
		setValueForMeta(getIndexForMeta("beLate"), "disabled", disabled);
		setValueForMeta(getIndexForMeta("absentStudentId"), "disabled", disabled);
		
		setValueForMeta(getIndexForMeta("teachingTime"), "needInput", needInput);
		setValueForMeta(getIndexForMeta("teachingAddress"), "needInput", needInput);
		//setValueForMeta(getIndexForMeta("currentTeachingPeriod"), "needInput", needInput);
	}
	
	function bind_radio_change(label){
		// 由于radio组件不能携带组件本身的信息来,所以如果一个界面多个组件需要注册事件,只能通过划分label值的范围进行区分
		if(this.modelName == "teaching"){
			var lastClassNumber = getValueForMeta(getIndexForMeta("lastClassNumber"), "value")
			// 正常上课, 需要恢复之前的默认选择情况
			if(label == 0 || label == 1){
				setTeachingRadioControl(false, true);
				setValueForMeta(getIndexForMeta("absentReason"), "disabled", label == 0 ? true : false);
				setValueForMeta(getIndexForMeta("currentClassNumber"), "value", lastClassNumber + 1);
				return;
			}
			// 取消的情况,上课时间以下所有字段置灰
			if(label == 2){
				setTeachingRadioControl(true, false);
				setValueForMeta(getIndexForMeta("absentReason"), "disabled", false);
				setValueForMeta(getIndexForMeta("currentClassNumber"), "value", lastClassNumber);
				
				// 如果清空之前的值得话,这样误点击radio按钮就被清空了
				/*
				setValueForMeta(getIndexForMeta("teachingAddress"), "value", '');
				setValueForMeta(getIndexForMeta("contactPhoneNumber"), "value", '');
				setValueForMeta(getIndexForMeta("currentTeachingPeriod"), "value", '');
				setValueForMeta(getIndexForMeta("teacherArriveTime"), "value", '');
				setValueForMeta(getIndexForMeta("absentStudentId"), "value", []);
				*/
			}
		}
	}
    
    function update_init(oneRow) {
        if(this.modelName == "teaching"){
            if(!!oneRow["classId"])
                loading_select_init(oneRow["classId"]);
        }
    }
	
	function load_init(){
		if(this.modelName == "student"){
			this.vm.loadRefs({
                model : "student",
                key : "studentId",
                value : "ifnull(max(globalId),20170250000) + 1 as globalId"
            }, function(response, index){
                var result = response.body;
				if( !!result && result.length > 0){
					var existId = result[0]["globalId"];
					var year = new Date().getFullYear();
					var newYearId = (year * 1000 + 25) * 1000 + 1;
					var setId = existId > newYearId ? existId : newYearId;
					setValueForMeta(getIndexForMeta("globalId"), "value", setId);
				}
            },0);
		}
	}

    function load_extra_data() {
        if(this.modelName == "teaching"){
            this.vm.loadRefs({
                model : "grade",
                key : "classId",
                value : "teacherId, teachingTime1, teachingTime2"
            }, function(response, index){
                this.teachingTime = response.body;
            },0);
            this.vm.loadRefs({
                model : "student",
                key : "classId",
                value : "studentId, chineseName, englishName"
            }, function(response, index){
                this.student = response.body;
                var translate = response.body.map(function (oneRow) {
                    return {value : oneRow["studentId"], desc : oneRow["chineseName"] + oneRow["englishName"]};
                });
                setTranslateByName("absentStudentId", translate);
            },0);
        }

    }

    global.translateDataBase2Js =  translateDataBase2Js;
    global.formatterColumn =  formatterColumn;
    global.data2View = data2View;
    
    global.load_extra_data =  load_extra_data;
    global.bind_select_change =  bind_select_change;
	global.load_init = load_init;
    global.update_init = update_init;
	global.bind_radio_change = bind_radio_change;

})(this);
