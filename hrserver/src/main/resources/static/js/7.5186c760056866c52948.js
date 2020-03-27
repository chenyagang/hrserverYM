webpackJsonp([7],{NB8z:function(e,t){},Scs9:function(e,t,i){var o={"./Demand.vue":"pXrn"};function l(e){return i(a(e))}function a(e){var t=o[e];if(!(t+1))throw new Error("Cannot find module '"+e+"'.");return t}l.keys=function(){return Object.keys(o)},l.resolve=a,e.exports=l,l.id="Scs9"},pXrn:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o={data:function(){return{emps:[],keywords:"",fileUploadBtnText:"导入数据",addDemand:"添加需求",beginDateScope:"",faangledoubleup:"fa-angle-double-up",faangledoubledown:"fa-angle-double-down",isShow:!1,dialogTitle:"",multipleSelection:[],depTextColor:"#c0c4cc",overflow:!0,nations:[],politics:[],positions:[],joblevels:[],totalCount:-1,currentPage:1,degrees:[{id:4,name:"大专"},{id:5,name:"本科"},{id:6,name:"硕士"},{id:7,name:"博士"},{id:3,name:"高中"},{id:2,name:"初中"},{id:1,name:"小学"},{id:8,name:"其他"}],deps:[],defaultProps:{label:"name",isLeaf:"leaf",children:"children"},dialogVisible:!1,tableLoading:!1,advanceSearchViewVisible:!1,showOrHidePop:!1,showOrHidePop2:!1,emp:{customer:"",department:"",jobName:"",jobType:"",jobResponsibility:"",jobDesc:"",degreeRequired:"",jobEmergency:"",jobPoints:"",quantityDemanded:"",workingPlace:"",interviewer:"",keyPoint:"",workOvertime:"",note:"",id:""},rules:{name:[{required:!0,message:"必填:姓名",trigger:"blur"}],jobName:[{required:!0,message:"必填:岗位名称",trigger:"blur"}],jobDesc:[{required:!0,message:"必填:岗位描述",trigger:"blur"}],quantityDemanded:[{required:!0,message:"必填:需求量",trigger:"blur"}],workingPlace:[{required:!0,message:"必填:工作地点",trigger:"blur"}],interviewer:[{required:!0,message:"必填:面试官",trigger:"blur"}]}}},mounted:function(){this.loadEmps()},methods:{fileUploadSuccess:function(e,t,i){e&&this.$message({type:e.status,message:e.msg}),this.loadEmps(),this.fileUploadBtnText="导入数据"},uploadResumeSuccess:function(e,t,i){e&&this.$message({type:e.status,message:e.msg}),this.loadEmps(),this.fileUploadBtnText="上传简历"},mouseEnter:function(e,t,i,o){0==this.isShow?this.isShow=!0:this.isShow=!1},fileUploadError:function(e,t,i){this.$message({type:"error",message:"导入失败!"}),this.fileUploadBtnText="导入数据"},uploadResumeError:function(e,t,i){this.$message({type:"error",message:"导入失败!"}),this.fileUploadBtnText="上传简历"},beforeFileUpload:function(e){this.fileUploadBtnText="正在导入"},exportEmps:function(){window.open("/employee/basic/exportEmp","_parent")},exportExecl:function(e){window.open("/employee/basic/exportExecl?id="+e.id,"_parent")},exportWord:function(e){window.open("/employee/basic/exportWord?id="+e.id,"_parent")},cancelSearch:function(){this.advanceSearchViewVisible=!1,this.emptyEmpData(),this.beginDateScope="",this.loadEmps()},showAdvanceSearchView:function(){this.advanceSearchViewVisible=!this.advanceSearchViewVisible,this.keywords="",this.advanceSearchViewVisible||(this.emptyEmpData(),this.beginDateScope="",this.loadEmps())},handleSelectionChange:function(e){this.multipleSelection=e},deleteManyEmps:function(){var e=this;this.$confirm("此操作将删除["+this.multipleSelection.length+"]条数据, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){for(var t="",i=0;i<e.multipleSelection.length;i++)t+=e.multipleSelection[i].id+",";e.doDelete(t)}).catch(function(){})},deleteEmp:function(e){var t=this;this.$confirm("此操作将永久删除["+e.name+"], 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.doDelete(e.id)}).catch(function(){})},doDelete:function(e){this.tableLoading=!0;var t=this;this.deleteRequest("/employee/basic/emp/"+e).then(function(e){if(t.tableLoading=!1,e&&200==e.status){e.data;_,t.loadEmps()}})},keywordsChange:function(e){""==e&&this.loadEmps()},searchEmp:function(){this.loadEmps()},currentChange:function(e){this.currentPage=e,this.loadEmps()},loadEmps:function(){var e=this,t=this;this.tableLoading=!0,this.getRequest("/requirement/selectByPage?page="+this.currentPage+"&size=10&customer="+this.keywords).then(function(i){if(e.tableLoading=!1,i&&200==i.status){var o=i.data;t.emps=o.emps,t.totalCount=o.count}})},addEmp:function(e){var t=this,i=this;this.$refs[e].validate(function(e){if(!e)return!1;t.emp.id?(t.tableLoading=!0,t.putRequest("/requirement/updateById",t.emp).then(function(e){if(i.tableLoading=!1,e&&200==e.status){e.data;i.dialogVisible=!1,i.emptyEmpData(),i.loadEmps()}})):(t.tableLoading=!0,t.postRequest("/requirement/insert",t.emp).then(function(e){if(i.tableLoading=!1,e&&200==e.status){e.data;i.dialogVisible=!1,i.emptyEmpData(),i.loadEmps()}}))})},cancelEidt:function(){this.dialogVisible=!1,this.emptyEmpData()},showDepTree:function(){this.showOrHidePop=!this.showOrHidePop},showDepTree2:function(){this.showOrHidePop2=!this.showOrHidePop2},handleNodeClick:function(e){this.emp.departmentName=e.name,this.emp.departmentId=e.id,this.showOrHidePop=!1,this.depTextColor="#606266"},handleNodeClick2:function(e){this.emp.departmentName=e.name,this.emp.departmentId=e.id,this.showOrHidePop2=!1,this.depTextColor="#606266"},showEditEmpView:function(e){this.dialogTitle="编辑需求",this.emp=e,this.emp.interviewTime=this.formatDate(e.interviewTime),this.emp.workTime=this.formatDate(e.workTime),this.dialogVisible=!0},showAddView:function(){this.dialogTitle="新增需求",this.emptyEmpData(),this.dialogVisible=!0},showAddEmpView:function(){this.dialogTitle="上传简历";var e=this;this.getRequest("/employee/basic/maxWorkID").then(function(t){t&&200==t.status&&(e.emp.workID=t.data)})},emptyEmpData:function(){this.emp={customer:"",department:"",jobName:"",jobType:"",jobResponsibility:"",jobDesc:"",degreeRequired:"",jobEmergency:"",jobPoints:"",quantityDemanded:"",workingPlace:"",interviewer:"",keyPoint:"",workOvertime:"",note:"",id:""}}}},l={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",[i("el-container",[i("el-header",{staticStyle:{padding:"0px",display:"flex","justify-content":"space-between","align-items":"center"}},[i("div",{staticStyle:{display:"inline"}},[i("el-input",{staticStyle:{width:"300px",margin:"0px",padding:"0px"},attrs:{placeholder:"通过客户名搜索客户,记得回车哦...",clearable:"",size:"mini",disabled:e.advanceSearchViewVisible,"prefix-icon":"el-icon-search"},on:{change:e.keywordsChange},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.searchEmp(t)}},model:{value:e.keywords,callback:function(t){e.keywords=t},expression:"keywords"}}),e._v(" "),i("el-button",{staticStyle:{"margin-left":"5px"},attrs:{type:"primary",size:"mini",icon:"el-icon-search"},on:{click:e.searchEmp}},[e._v("搜索\n        ")])],1),e._v(" "),i("div",{staticStyle:{"margin-right":"20px"}},[i("el-button",{attrs:{size:"mini",type:"primary"},on:{click:function(t){e.showAddView()}}},[i("i",{staticClass:"fa fa-lg fa-level-up",staticStyle:{"margin-left":"5px"}}),e._v(e._s(e.addDemand)+"\n        ")])],1)]),e._v(" "),i("el-main",{staticStyle:{"padding-left":"0px","padding-top":"0px"}},[i("div",[i("transition",{attrs:{name:"slide-fade"}},[i("div",{directives:[{name:"show",rawName:"v-show",value:e.advanceSearchViewVisible,expression:"advanceSearchViewVisible"}],staticStyle:{"margin-bottom":"10px",border:"1px","border-radius":"5px","border-style":"solid",padding:"5px 0px 5px 0px","box-sizing":"border-box","border-color":"#20a0ff"}},[i("el-row",[i("el-col",{attrs:{span:5}},[e._v("\n                政治面貌:\n                "),i("el-select",{staticStyle:{width:"130px"},attrs:{size:"mini",placeholder:"政治面貌"},model:{value:e.emp.politicId,callback:function(t){e.$set(e.emp,"politicId",t)},expression:"emp.politicId"}},e._l(e.politics,function(e){return i("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})}))],1),e._v(" "),i("el-col",{attrs:{span:4}},[e._v("\n                民族:\n                "),i("el-select",{staticStyle:{width:"130px"},attrs:{size:"mini",placeholder:"请选择民族"},model:{value:e.emp.nationId,callback:function(t){e.$set(e.emp,"nationId",t)},expression:"emp.nationId"}},e._l(e.nations,function(e){return i("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})}))],1)],1),e._v(" "),i("el-row",{staticStyle:{"margin-top":"10px"}},[i("el-col",{attrs:{span:10}},[e._v("\n                入职日期:\n                "),i("el-date-picker",{attrs:{"unlink-panels":"",size:"mini",type:"daterange","value-format":"yyyy-MM-dd","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期"},model:{value:e.beginDateScope,callback:function(t){e.beginDateScope=t},expression:"beginDateScope"}})],1),e._v(" "),i("el-col",{attrs:{span:5,offset:4}},[i("el-button",{attrs:{size:"mini"},on:{click:e.cancelSearch}},[e._v("取消")]),e._v(" "),i("el-button",{attrs:{icon:"el-icon-search",type:"primary",size:"mini"},on:{click:e.searchEmp}},[e._v("搜索")])],1)],1)],1)]),e._v(" "),i("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],staticStyle:{width:"100%"},attrs:{data:e.emps,stripe:"",size:"mini"},on:{"row-click":e.mouseEnter,"selection-change":e.handleSelectionChange}},[i("el-table-column",{attrs:{type:"selection",align:"center",width:"30"}}),e._v(" "),i("el-table-column",{attrs:{width:"30",type:"expand"},scopedSlots:e._u([{key:"default",fn:function(t){return[i("el-form",{staticClass:"demo-table-expand",attrs:{"label-position":"left",inline:""}},[i("el-form-item",{attrs:{label:"客户"}},[i("span",[e._v(e._s(t.row.customer))])]),e._v(" "),i("el-form-item",{attrs:{label:"需求部门"}},[i("span",[e._v(e._s(t.row.department))])]),e._v(" "),i("el-form-item",{attrs:{label:"岗位名称"}},[i("span",[e._v(e._s(t.row.jobName))])]),e._v(" "),i("el-form-item",{attrs:{label:"岗位类型"}},[i("span",[e._v(e._s(t.row.jobType))])]),e._v(" "),i("el-form-item",{attrs:{label:"岗位职责"}},[i("span",[e._v(e._s(t.row.jobResponsibility))])]),e._v(" "),i("el-form-item",{attrs:{label:"岗位描述"}},[i("span",[e._v(e._s(t.row.jobDesc))])]),e._v(" "),i("el-form-item",{attrs:{label:"学历要求"}},[i("span",[e._v(e._s(t.row.degreeRequired))])]),e._v(" "),i("el-form-item",{attrs:{label:"岗位紧急程度"}},[i("span",[e._v(e._s(t.row.jobEmergency))])]),e._v(" "),i("el-form-item",{attrs:{label:"岗位要点"}},[i("span",[e._v(e._s(t.row.jobPoints))])]),e._v(" "),i("el-form-item",{attrs:{label:"需求数量"}},[i("span",[e._v(e._s(t.row.quantityDemanded))])]),e._v(" "),i("el-form-item",{attrs:{label:"工作地点"}},[i("span",[e._v(e._s(t.row.workingPlace))])]),e._v(" "),i("el-form-item",{attrs:{label:"面试官"}},[i("span",[e._v(e._s(t.row.interviewer))])]),e._v(" "),i("el-form-item",{attrs:{label:"总结关键点"}},[i("span",[e._v(e._s(t.row.keyPoint))])]),e._v(" "),i("el-form-item",{attrs:{label:"考勤情况"}},[i("span",[e._v(e._s(t.row.workOvertime))])]),e._v(" "),i("el-form-item",{attrs:{label:"备注"}},[i("span",[e._v(e._s(t.row.note))])])],1)]}}])}),e._v(" "),i("el-table-column",{attrs:{prop:"customer",align:"center","show-overflow-tooltip":e.overflow,fixed:"",label:"客户",width:"70"}}),e._v(" "),i("el-table-column",{attrs:{prop:"department",align:"center","show-overflow-tooltip":e.overflow,label:"需求部门",width:"100"}}),e._v(" "),i("el-table-column",{attrs:{prop:"jobName",align:"center","show-overflow-tooltip":e.overflow,label:"岗位名称",width:"100"}}),e._v(" "),i("el-table-column",{attrs:{prop:"jobType",align:"center","show-overflow-tooltip":e.overflow,label:"岗位类型",width:"100"}}),e._v(" "),i("el-table-column",{attrs:{prop:"jobResponsibility",width:"85","show-overflow-tooltip":e.overflow,align:"center",label:"岗位职责"}}),e._v(" "),i("el-table-column",{attrs:{prop:"jobDesc",align:"center","show-overflow-tooltip":e.overflow,label:"岗位描述",width:"150"}}),e._v(" "),i("el-table-column",{attrs:{prop:"degreeRequired",label:"学历要求","show-overflow-tooltip":e.overflow,align:"center",width:"70"}}),e._v(" "),i("el-table-column",{attrs:{prop:"jobEmergency",label:"岗位紧急程度",align:"center","show-overflow-tooltip":e.overflow,width:"50"}}),e._v(" "),i("el-table-column",{attrs:{prop:"jobPoints",label:"岗位要点","show-overflow-tooltip":e.overflow,align:"center",width:"100"}}),e._v(" "),i("el-table-column",{attrs:{prop:"quantityDemanded",label:"需求数量",align:"center","show-overflow-tooltip":e.overflow,width:"50"}}),e._v(" "),i("el-table-column",{attrs:{prop:"workingPlace",label:"工作地点","show-overflow-tooltip":e.overflow,align:"center",width:"70"}}),e._v(" "),i("el-table-column",{attrs:{prop:"interviewer",label:"面试官","show-overflow-tooltip":e.overflow,align:"center",width:"70"}}),e._v(" "),i("el-table-column",{attrs:{prop:"keyPoint",align:"center",label:"总结关键点","show-overflow-tooltip":e.overflow,width:"70"}}),e._v(" "),i("el-table-column",{attrs:{prop:"workOvertime",label:"考勤情况",align:"center","show-overflow-tooltip":e.overflow,width:"100"}}),e._v(" "),i("el-table-column",{attrs:{prop:"note",align:"center","show-overflow-tooltip":e.overflow,label:"备注",width:"100"}}),e._v(" "),e.isShow?i("el-table-column",{attrs:{fixed:"right",label:"操作",width:"195"},scopedSlots:e._u([{key:"default",fn:function(t){return[i("el-button",{staticStyle:{padding:"3px 4px 3px 4px",margin:"2px"},attrs:{size:"mini"},on:{click:function(i){e.showEditEmpView(t.row)}}},[e._v("编辑\n              ")])]}}])}):e._e()],1),e._v(" "),i("div",{staticStyle:{display:"flex","justify-content":"space-between",margin:"2px"}},[i("el-pagination",{attrs:{background:"","page-size":10,"current-page":e.currentPage,layout:"prev, pager, next",total:e.totalCount},on:{"current-change":e.currentChange}})],1)],1)])],1),e._v(" "),i("el-form",{ref:"addEmpForm",staticStyle:{margin:"0px",padding:"0px"},attrs:{model:e.emp,rules:e.rules}},[i("div",{staticStyle:{"text-align":"left"}},[i("el-dialog",{staticStyle:{padding:"0px"},attrs:{title:e.dialogTitle,"close-on-click-modal":!1,visible:e.dialogVisible,width:"77%"},on:{"update:visible":function(t){e.dialogVisible=t}}},[i("el-row",[i("el-col",{attrs:{span:6}},[i("div",[i("el-form-item",{attrs:{label:"客户:",prop:"customer"}},[i("el-input",{staticStyle:{width:"50%"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",placeholder:"请输入客户姓名"},model:{value:e.emp.customer,callback:function(t){e.$set(e.emp,"customer",t)},expression:"emp.customer"}})],1)],1)]),e._v(" "),i("el-col",{attrs:{span:6}},[i("div",[i("el-form-item",{attrs:{label:"需求部门:",prop:"department"}},[i("el-input",{staticStyle:{width:"50%"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",placeholder:"需求部门"},model:{value:e.emp.department,callback:function(t){e.$set(e.emp,"department",t)},expression:"emp.department"}})],1)],1)]),e._v(" "),i("el-col",{attrs:{span:6}},[i("div",[i("el-form-item",{attrs:{label:"岗位名称:",prop:"jobName"}},[i("el-input",{staticStyle:{width:"50%"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",placeholder:"岗位名称..."},model:{value:e.emp.jobName,callback:function(t){e.$set(e.emp,"jobName",t)},expression:"emp.jobName"}})],1)],1)]),e._v(" "),i("el-col",{attrs:{span:5}},[i("div",[i("el-form-item",{attrs:{label:"岗位类型:",prop:"jobType"}},[i("el-input",{staticStyle:{width:"50%"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",placeholder:"岗位类型..."},model:{value:e.emp.jobType,callback:function(t){e.$set(e.emp,"jobType",t)},expression:"emp.jobType"}})],1)],1)])],1),e._v(" "),i("el-row",[i("el-col",{attrs:{span:6}},[i("div",[i("el-form-item",{attrs:{label:"学历:",prop:"degreeRequired"}},[i("el-select",{staticStyle:{width:"150px"},attrs:{size:"mini",placeholder:"学历要求"},model:{value:e.emp.degreeRequired,callback:function(t){e.$set(e.emp,"degreeRequired",t)},expression:"emp.degreeRequired"}},e._l(e.degrees,function(e){return i("el-option",{key:e.id,attrs:{label:e.name,value:e.name}})}))],1)],1)]),e._v(" "),i("el-col",{attrs:{span:6}},[i("div",[i("el-form-item",{attrs:{label:"岗位紧急程度:",prop:"jobEmergency"}},[i("el-input-number",{staticStyle:{width:"160px"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",placeholder:"岗位紧急程度"},model:{value:e.emp.jobEmergency,callback:function(t){e.$set(e.emp,"jobEmergency",t)},expression:"emp.jobEmergency"}})],1)],1)]),e._v(" "),i("el-col",{attrs:{span:6}},[i("div",[i("el-form-item",{attrs:{label:"需求量:",prop:"quantityDemanded"}},[i("el-input-number",{staticStyle:{width:"160px"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",placeholder:"需求量"},model:{value:e.emp.quantityDemanded,callback:function(t){e.$set(e.emp,"quantityDemanded",t)},expression:"emp.quantityDemanded"}})],1)],1)]),e._v(" "),i("el-col",{attrs:{span:6}},[i("div",[i("el-form-item",{attrs:{label:"工作地点:",prop:"workingPlace"}},[i("el-input",{staticStyle:{width:"50%"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",placeholder:"工作地点..."},model:{value:e.emp.workingPlace,callback:function(t){e.$set(e.emp,"workingPlace",t)},expression:"emp.workingPlace"}})],1)],1)])],1),e._v(" "),i("el-row",[i("el-col",{attrs:{span:6}},[i("div",[i("el-form-item",{attrs:{label:"关键点:",prop:"keyPoint"}},[i("el-input",{staticStyle:{width:"50%"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",placeholder:"关键点..."},model:{value:e.emp.keyPoint,callback:function(t){e.$set(e.emp,"keyPoint",t)},expression:"emp.keyPoint"}})],1)],1)]),e._v(" "),i("el-col",{attrs:{span:6}},[i("div",[i("el-form-item",{attrs:{label:"岗位职责:",prop:"jobResponsibility"}},[i("el-input",{staticStyle:{width:"50%"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",placeholder:"岗位职责..."},model:{value:e.emp.jobResponsibility,callback:function(t){e.$set(e.emp,"jobResponsibility",t)},expression:"emp.jobResponsibility"}})],1)],1)])],1),e._v(" "),i("el-row",[i("el-col",{attrs:{span:24}},[i("div",[i("el-form-item",{attrs:{label:"岗位描述:",prop:"jobDesc"}},[i("el-input",{staticStyle:{width:"90%","margin-left":"-10px"},attrs:{"prefix-icon":"el-icon-phone",size:"mini",type:"textarea",rows:"8",col:"24",placeholder:"岗位描述..."},model:{value:e.emp.jobDesc,callback:function(t){e.$set(e.emp,"jobDesc",t)},expression:"emp.jobDesc"}})],1)],1)])],1),e._v(" "),i("el-row",[i("el-col",{attrs:{span:24}},[i("div",[i("el-form-item",{attrs:{label:"岗位要点:",prop:"jobPoints"}},[i("el-input",{staticStyle:{width:"90%"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",type:"textarea",rows:"8",col:"24",placeholder:"岗位要点"},model:{value:e.emp.jobPoints,callback:function(t){e.$set(e.emp,"jobPoints",t)},expression:"emp.jobPoints"}})],1)],1)])],1),e._v(" "),i("el-row",[i("el-col",{attrs:{span:24}},[i("el-form-item",{attrs:{label:"考勤情况:",prop:"workOvertime"}},[i("div",[i("el-input",{staticStyle:{width:"90%"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",type:"textarea",rows:"8",col:"24",placeholder:"考勤情况"},model:{value:e.emp.workOvertime,callback:function(t){e.$set(e.emp,"workOvertime",t)},expression:"emp.workOvertime"}})],1)])],1)],1),e._v(" "),i("el-row",[i("el-col",{attrs:{span:24}},[i("el-form-item",{attrs:{label:"备注:",prop:"note"}},[i("div",[i("el-input",{staticStyle:{"margin-left":"30px",width:"90%"},attrs:{"prefix-icon":"el-icon-edit",size:"mini",type:"textarea",rows:"8",col:"24",placeholder:"备注"},model:{value:e.emp.note,callback:function(t){e.$set(e.emp,"note",t)},expression:"emp.note"}})],1)])],1)],1),e._v(" "),i("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{attrs:{size:"mini"},on:{click:e.cancelEidt}},[e._v("取 消")]),e._v(" "),i("el-button",{attrs:{size:"mini",type:"primary"},on:{click:function(t){e.addEmp("addEmpForm")}}},[e._v("确 定")])],1)],1)],1)])],1)},staticRenderFns:[]};var a=i("VU/8")(o,l,!1,function(e){i("NB8z")},null,null);t.default=a.exports}});
//# sourceMappingURL=7.5186c760056866c52948.js.map