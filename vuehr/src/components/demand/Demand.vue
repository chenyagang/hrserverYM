<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过客户名搜索客户,记得回车哦..."
            clearable
            @change="keywordsChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            :disabled="advanceSearchViewVisible"
            @keyup.enter.native="searchEmp"
            prefix-icon="el-icon-search"
            v-model="keywords">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchEmp">搜索
          </el-button>
          <!-- <el-button slot="reference" type="primary" size="mini" style="margin-left: 5px"
                      @click="showAdvanceSearchView"><i
             class="fa fa-lg" v-bind:class="[advanceSearchViewVisible ? faangledoubleup:faangledoubledown]"
             style="margin-right: 5px"></i>高级搜索
           </el-button>-->
        </div>
        <div style="margin-right: 20px">
          <el-button size="mini" type="primary" @click="showAddView()"><i class="fa fa-lg fa-level-up" style="margin-left: 5px"></i>{{addDemand}}
          </el-button>
        </div>
      </el-header>
      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <transition name="slide-fade">
            <div
              style="margin-bottom: 10px;border: 1px;border-radius: 5px;border-style: solid;padding: 5px 0px 5px 0px;box-sizing:border-box;border-color: #20a0ff"
              v-show="advanceSearchViewVisible">
              <el-row>
                <el-col :span="5">
                  政治面貌:
                  <el-select v-model="emp.politicId" style="width: 130px" size="mini" placeholder="政治面貌">
                    <el-option
                      v-for="item in politics"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="4">
                  民族:
                  <el-select v-model="emp.nationId" style="width: 130px" size="mini" placeholder="请选择民族">
                    <el-option
                      v-for="item in nations"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
              </el-row>
              <el-row style="margin-top: 10px">
                <el-col :span="10">
                  入职日期:
                  <el-date-picker
                    v-model="beginDateScope"
                    unlink-panels
                    size="mini"
                    type="daterange"
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                  </el-date-picker>
                </el-col>
                <el-col :span="5" :offset="4">
                  <el-button size="mini" @click="cancelSearch">取消</el-button>
                  <el-button icon="el-icon-search" type="primary" size="mini" @click="searchEmp">搜索</el-button>
                </el-col>
              </el-row>
            </div>
          </transition>

          <el-table
            :data="emps"
            v-loading="tableLoading"
            stripe
            @selection-change="handleSelectionChange"
            size="mini"
            style="width: 100%">

            <el-table-column
              type="selection"
              align="center"
              width="30">
            </el-table-column>

            <el-table-column
              width="30"
              type="expand">
              <template slot-scope="scope">
                <el-form label-position="left" inline class="demo-table-expand">
                  <el-form-item label="客户">
                    <span>{{ scope.row.customer }}</span>
                  </el-form-item>
                  <el-form-item label="需求部门">
                    <span>{{ scope.row.department }}</span>
                  </el-form-item>
                  <el-form-item label="岗位名称">
                    <span>{{ scope.row.jobName }}</span>
                  </el-form-item>
                  <el-form-item label="岗位类型">
                    <span>{{ scope.row.jobType }}</span>
                  </el-form-item>
                  <el-form-item label="岗位职责">
                    <span>{{ scope.row.jobResponsibility }}</span>
                  </el-form-item>
                  <el-form-item label="岗位描述">
                    <span>{{ scope.row.jobDesc }}</span>
                  </el-form-item>
                  <el-form-item label="学历要求">
                    <span>{{ scope.row.degreeRequired }}</span>
                  </el-form-item>
                  <el-form-item label="岗位紧急程度">
                    <span>{{ scope.row.jobEmergency }}</span>
                  </el-form-item>
                  <el-form-item label="岗位要点">
                    <span>{{ scope.row.jobPoints }}</span>
                  </el-form-item>
                  <el-form-item label="需求数量">
                    <span>{{ scope.row.quantityDemanded }}</span>
                  </el-form-item>
                  <el-form-item label="工作地点">
                    <span>{{ scope.row.workingPlace }}</span>
                  </el-form-item>
                  <el-form-item label="面试官">
                    <span>{{ scope.row.interviewer }}</span>
                  </el-form-item>
                  <el-form-item label="总结关键点">
                    <span>{{ scope.row.keyPoint }}</span>
                  </el-form-item>
                  <el-form-item label="考勤情况">
                    <span>{{ scope.row.workOvertime }}</span>
                  </el-form-item>
                  <el-form-item label="备注">
                    <span>{{ scope.row.note }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>

            <el-table-column
              prop="customer"
              align="center"
              :show-overflow-tooltip="overflow"
              fixed
              label="客户"
              width="70">
            </el-table-column>
            <el-table-column
              prop="department"
              align="center"
              :show-overflow-tooltip="overflow"
              label="需求部门"
              width="100">
            </el-table-column>
            <el-table-column
              prop="jobName"
              align="center"
              :show-overflow-tooltip="overflow"
              label="岗位名称"
              width="100">
            </el-table-column>
            <el-table-column
              prop="jobType"
              align="center"
              :show-overflow-tooltip="overflow"
              label="岗位类型"
              width="100">
            </el-table-column>
            <el-table-column
              prop="jobResponsibility"
              width="85"
              :show-overflow-tooltip="overflow"
              align="center"
              label="岗位职责">
            </el-table-column>
            <el-table-column
              prop="jobDesc"
              align="center"
              :show-overflow-tooltip="overflow"
              label="岗位描述"
              width="150">
            </el-table-column>
            <el-table-column
              prop="degreeRequired"
              label="学历要求"
              :show-overflow-tooltip="overflow"
              align="center"
              width="70">
            </el-table-column>
            <el-table-column
              prop="jobEmergency"
              label="岗位紧急程度"
              align="center"
              :show-overflow-tooltip="overflow"
              width="50">
            </el-table-column>
            <el-table-column
              prop="jobPoints"
              label="岗位要点"
              :show-overflow-tooltip="overflow"
              align="center"
              width="100">
            </el-table-column>
            <el-table-column
              prop="quantityDemanded"
              label="需求数量"
              align="center"
              :show-overflow-tooltip="overflow"
              width="50">
            </el-table-column>
            <el-table-column
              prop="workingPlace"
              label="工作地点"
              :show-overflow-tooltip="overflow"
              align="center"
              width="70">
            </el-table-column>
            <el-table-column
              prop="interviewer"
              label="面试官"
              :show-overflow-tooltip="overflow"
              align="center"
              width="70">
            </el-table-column>
            <el-table-column
              prop="keyPoint"
              align="center"
              label="总结关键点"
              :show-overflow-tooltip="overflow"
              width="70">
            </el-table-column>
            <el-table-column
              prop="workOvertime"
              label="考勤情况"
              align="center"
              :show-overflow-tooltip="overflow"
              width="100">
            </el-table-column>
            <el-table-column
              prop="note"
              align="center"
              :show-overflow-tooltip="overflow"
              label="备注"
              width="100">
            </el-table-column>

            <el-table-column
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditEmpView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <!-- <el-button type="danger" size="mini" v-if="emps.length>0" :disabled="multipleSelection.length==0"
                        @click="deleteManyEmps">批量删除
             </el-button>-->
            <el-pagination
              background
              :page-size="10"
              :current-page="currentPage"
              @current-change="currentChange"
              layout="prev, pager, next"
              :total="totalCount">
            </el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>
    <el-form :model="emp" :rules="rules" ref="addEmpForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="77%">
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="客户:" prop="customer">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.customer" size="mini" style="width: 150px"
                            placeholder="请输入客户姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="需求部门:" prop="department">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.department" size="mini" style="width: 150px"
                            placeholder="需求部门"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="岗位名称:" prop="jobName">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.jobName" size="mini" style="width: 150px"
                            placeholder="岗位名称..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="岗位类型:" prop="jobType">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.jobType" size="mini" style="width: 150px"
                            placeholder="岗位类型..."></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <!--<el-col :span="6">
              <div>
                <el-form-item label="岗位描述:" prop="jobDesc">
                    <el-input prefix-icon="el-icon-phone" v-model="emp.jobDesc" size="mini" style="width: 150px"
                              placeholder="岗位描述..."></el-input>
                </el-form-item>
              </div>
            </el-col>-->
            <el-col :span="6">
              <div>
                  <el-form-item label="学历:" prop="degreeRequired">
                    <el-select v-model="emp.degreeRequired" style="width: 150px" size="mini" placeholder="学历要求">
                      <el-option
                        v-for="item in degrees"
                        :key="item.id"
                        :label="item.name"
                        :value="item.name">
                      </el-option>
                    </el-select>
                  </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="岗位紧急程度:" prop="jobEmergency">
                  <el-input-number prefix-icon="el-icon-edit" v-model="emp.jobEmergency"  size="mini"
                                   style="width: 160px"    placeholder="岗位紧急程度"></el-input-number>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="需求量:" prop="quantityDemanded">
                  <el-input-number prefix-icon="el-icon-edit" v-model="emp.quantityDemanded"  size="mini"
                                   style="width: 160px"    placeholder="需求量"></el-input-number>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="工作地点:" prop="workingPlace">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.workingPlace" size="mini" style="width: 140px"
                            placeholder="工作地点..."></el-input>
                </el-form-item>
              </div>
            </el-col>

          </el-row>
          <el-row>
          <!--  <el-col :span="6">
              <div>
                <el-form-item label="需求量:" prop="quantityDemanded">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.quantityDemanded" size="mini" style="width: 160px"
                            placeholder="需求量..."></el-input>
                </el-form-item>
              </div>
            </el-col>-->
            <el-col :span="6">
              <div>
                <el-form-item label="关键点:" prop="keyPoint">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.keyPoint" size="mini" style="width: 160px"
                            placeholder="关键点..."></el-input>
                </el-form-item>
              </div>
            </el-col>

            <el-col :span="6">
              <div>
                <el-form-item label="岗位职责:" prop="jobResponsibility">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.jobResponsibility" size="mini" style="width: 160px"
                            placeholder="岗位职责..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <!--  <el-col :span="6">
                <div>
                  <el-form-item label="考勤情况:" prop="workOvertime">
                    <el-input prefix-icon="el-icon-edit" v-model="emp.workOvertime" size="mini" style="width: 150px"
                              placeholder="考勤情况..."></el-input>
                  </el-form-item>
                </div>
              </el-col>-->
          </el-row>
          <el-row>
            <el-col :span="24">
              <div>
                <el-form-item label="岗位描述:" prop="jobDesc">
                  <el-input prefix-icon="el-icon-phone" v-model="emp.jobDesc" size="mini"
                            type="textarea"
                            rows="8"
                            col="24"
                            style="width: 1030px;margin-left:-10px;"
                            placeholder="岗位描述..."></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div>
                <el-form-item label="岗位要点:" prop="jobPoints">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.jobPoints" size="mini"
                            type="textarea"
                            rows="8"
                            col="24"
                            style="width: 1030px"
                            placeholder="岗位要点"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
                <el-form-item label="考勤情况:" prop="workOvertime">
                  <el-col :span="6">
                    <div>
                      <el-input prefix-icon="el-icon-edit" v-model="emp.workOvertime" size="mini"
                                type="textarea"
                                rows="8"
                                col="24"
                                style="width: 1030px"
                                placeholder="考勤情况"></el-input>
                    </div>
                  </el-col>
                </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="备注:" prop="note">
                <el-col :span="6">
                  <div>
                    <el-input prefix-icon="el-icon-edit" v-model="emp.note" size="mini"
                              type="textarea"
                              rows="8"
                              col="24"
                              style="margin-left:30px;width: 1030px"
                              placeholder="备注"></el-input>
                  </div>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEidt">取 消</el-button>
    <el-button size="mini" type="primary" @click="addEmp('addEmpForm')">确 定</el-button>
  </span>
        </el-dialog>
      </div>
    </el-form>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        emps: [],
        keywords: '',
        fileUploadBtnText: '导入数据',
        addDemand: '添加需求',
        beginDateScope: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        multipleSelection: [],
        depTextColor: '#c0c4cc',
        overflow:true,
        nations: [],
        politics: [],
        positions: [],
        joblevels: [],
        totalCount: -1,
        currentPage: 1,
        degrees: [{id: 4, name: '大专'}, {id: 5, name: '本科'}, {id: 6, name: '硕士'}, {id: 7, name: '博士'}, {
          id: 3,
          name: '高中'
        }, {id: 2, name: '初中'}, {id: 1, name: '小学'}, {id: 8, name: '其他'}],
        deps: [],
        defaultProps: {
          label: 'name',
          isLeaf: 'leaf',
          children: 'children'
        },
        dialogVisible: false,
        tableLoading: false,
        advanceSearchViewVisible: false,
        showOrHidePop: false,
        showOrHidePop2: false,
        emp: {
          customer: '',
          department: '',
          jobName: '',
          jobType: '',
          jobResponsibility: '',
          jobDesc: '',
          degreeRequired: '',
          jobEmergency: '',
          jobPoints: '',
          quantityDemanded: '',
          workingPlace: '',
          interviewer: '',
          keyPoint: '',
          workOvertime: '',
          note: '',
          id: ''
        },
        rules: {
          name: [{required: true, message: '必填:姓名', trigger: 'blur'}],
          jobName: [{required: true, message: '必填:岗位名称', trigger: 'blur'}],
          jobDesc: [{required: true, message: '必填:岗位描述', trigger: 'blur'}],
          quantityDemanded: [{required: true, message: '必填:需求量', trigger: 'blur'}],
          workingPlace: [{required: true, message: '必填:工作地点', trigger: 'blur'}],
          interviewer: [{required: true, message: '必填:面试官', trigger: 'blur'}],
        }
      }
        ;
    },
    mounted: function () {
      // this.initData();
      this.loadEmps();
    },
    methods: {
      fileUploadSuccess(response, file, fileList) {
        if (response) {
          this.$message({type: response.status, message: response.msg});
        }
        this.loadEmps();
        this.fileUploadBtnText = '导入数据';
      },
      uploadResumeSuccess(response, file, fileList) {
        if (response) {
          this.$message({type: response.status, message: response.msg});
        }
        this.loadEmps();
        this.fileUploadBtnText = '上传简历';
      },
      fileUploadError(err, file, fileList) {
        this.$message({type: 'error', message: "导入失败!"});
        this.fileUploadBtnText = '导入数据';
      },
      uploadResumeError(err, file, fileList) {
        this.$message({type: 'error', message: "导入失败!"});
        this.fileUploadBtnText = '上传简历';
      },
      beforeFileUpload(file) {
        this.fileUploadBtnText = '正在导入';
      },
      exportEmps() {
        window.open("/employee/basic/exportEmp", "_parent");
      },
      exportExecl(row) {
        window.open("/employee/basic/exportExecl?id=" + row.id, "_parent");
      },
      exportWord(row) {
        window.open("/employee/basic/exportWord?id=" + row.id, "_parent");
      },
      cancelSearch() {
        this.advanceSearchViewVisible = false;
        this.emptyEmpData();
        this.beginDateScope = '';
        this.loadEmps();
      },

      showAdvanceSearchView() {
        this.advanceSearchViewVisible = !this.advanceSearchViewVisible;
        this.keywords = '';
        if (!this.advanceSearchViewVisible) {
          this.emptyEmpData();
          this.beginDateScope = '';
          this.loadEmps();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      deleteManyEmps() {
        this.$confirm('此操作将删除[' + this.multipleSelection.length + ']条数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var ids = '';
          for (var i = 0; i < this.multipleSelection.length; i++) {
            ids += this.multipleSelection[i].id + ",";
          }
          this.doDelete(ids);
        }).catch(() => {
        });
      },
      deleteEmp(row) {
        this.$confirm('此操作将永久删除[' + row.name + '], 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.doDelete(row.id);
        }).catch(() => {
        });
      },
      doDelete(ids) {
        this.tableLoading = true;
        var _this = this;
        this.deleteRequest("/employee/basic/emp/" + ids).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _
            _this.loadEmps();
          }
        })
      },
      keywordsChange(val) {
        if (val == '') {
          this.loadEmps();
        }
      },
      searchEmp() {
        this.loadEmps();
      },
      currentChange(currentChange) {
        this.currentPage = currentChange;
        this.loadEmps();
      },
      loadEmps() {
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/requirement/selectByPage?page=" + this.currentPage + "&size=10&customer=" + this.keywords).then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            debugger
            var data = resp.data;
            debugger
            _this.emps = data.emps;
            _this.totalCount = data.count;
          }
        })
      },
      addEmp(formName) {
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.emp.id) {
              //更新
              this.tableLoading = true;
              this.putRequest("/requirement/updateById", this.emp).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyEmpData();
                  _this.loadEmps();
                }
              })
            } else {
              //添加
              this.tableLoading = true;
              this.postRequest("/requirement/insert", this.emp).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  debugger
                  _this.dialogVisible = false;
                  _this.emptyEmpData();
                  _this.loadEmps();
                }
              })
            }
          } else {
            return false;
          }
        });
      },
      cancelEidt() {
        this.dialogVisible = false;
        this.emptyEmpData();
      },
      showDepTree() {
        this.showOrHidePop = !this.showOrHidePop;
      },
      showDepTree2() {
        this.showOrHidePop2 = !this.showOrHidePop2;
      },
      handleNodeClick(data) {
        this.emp.departmentName = data.name;
        this.emp.departmentId = data.id;
        this.showOrHidePop = false;
        this.depTextColor = '#606266';
      },
      handleNodeClick2(data) {
        this.emp.departmentName = data.name;
        this.emp.departmentId = data.id;
        this.showOrHidePop2 = false;
        this.depTextColor = '#606266';
      },
      // 初始化选框数据
      // initData() {
      //   var _this = this;
      //   this.getRequest("/employee/basic/basicdata").then(resp => {
      //     if (resp && resp.status == 200) {
      //       var data = resp.data;
      //       _this.nations = data.nations;
      //       _this.politics = data.politics;
      //       _this.deps = data.deps;
      //       _this.positions = data.positions;
      //       _this.joblevels = data.joblevels;
      //       _this.emp.workID = data.workID;
      //     }
      //   })
      // },
      showEditEmpView(row) {
        debugger
        this.dialogTitle = "编辑需求";
        this.emp = row;

        this.emp.interviewTime = this.formatDate(row.interviewTime);
        this.emp.workTime = this.formatDate(row.workTime);
        this.dialogVisible = true;
      },
      showAddView() {

        this.dialogTitle = "新增需求";
        this.emptyEmpData();
        this.dialogVisible = true;
      },

      showAddEmpView() {
        this.dialogTitle = "上传简历";
        // this.dialogVisible = true;
        var _this = this;
        this.getRequest("/employee/basic/maxWorkID").then(resp => {
          if (resp && resp.status == 200) {
            _this.emp.workID = resp.data;
          }
        })
      },
      emptyEmpData() {
        this.emp= {
          customer: '',
            department: '',
            jobName: '',
            jobType: '',
            jobResponsibility: '',
            jobDesc: '',
            degreeRequired: '',
            jobEmergency: '',
            jobPoints: '',
            quantityDemanded: '',
            workingPlace: '',
            interviewer: '',
            keyPoint: '',
            workOvertime: '',
            note: '',
            id: ''
        }
      }
    }
  };
</script>
<style>
  .el-dialog__body {
    padding-top: 0px;
    padding-bottom: 0px;
  }

  .slide-fade-enter-active {
    transition: all .8s ease;
  }

  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }

  .slide-fade-enter, .slide-fade-leave-to {
    transform: translateX(10px);
    opacity: 0;
  }

  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 90%;
  }

</style>
