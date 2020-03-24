<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过员工名搜索员工,记得回车哦..."
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
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-upload
            :show-file-list="false"
            accept="application/vnd.ms-excel"
            action="/employee/basic/importEmp"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError" :disabled="fileUploadBtnText=='正在导入'"
            :before-upload="beforeFileUpload" style="display: inline">
            <!-- <el-button size="mini" type="success" :loading="fileUploadBtnText=='正在导入'"><i class="fa fa-lg fa-level-up"
                                                                                           style="margin-right: 5px"></i>{{fileUploadBtnText}}
             </el-button>-->
          </el-upload>
          <el-upload
            :show-file-list="false"
            accept="application/vnd.ms-excel"
            action="/employee/basic/importEmp"
            :on-success="uploadResumeSuccess"
            :on-error="uploadResumeError" :disabled="fileUploadBtnText=='正在导入'"
            :before-upload="beforeFileUpload" style="display: inline">
            <el-button size="mini" type="primary" :loading="fileUploadBtnText=='正在导入'"><i class="fa fa-lg fa-level-up"
                                                                                          style="margin-right: 5px"></i>{{uploadResumeBtnText}}
            </el-button>
          </el-upload>
          <el-button type="success" size="mini" @click="exportEmps"><i class="fa fa-lg fa-level-down"
                                                                       style="margin-right: 5px"></i>批量导出数据
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
            border
            stripe
            @row-click="rightClick"
            @selection-change="handleSelectionChange"
            size="mini"
            style="width: 100%">

            <el-table-column
              type="selection"
              align="center"
              width="30">
            </el-table-column>
            <el-table-column
              prop="name"
              align="center"
              fixed
              label="姓名"
              width="70">
            </el-table-column>
            <el-table-column
              prop="phone"
              label="电话"
              align="center"
              width="100">
            </el-table-column>
            <el-table-column
              prop="school"
              label="学校"
              align="center"
              width="100">
            </el-table-column>
            <el-table-column
              prop="tiptopDegree"
              label="学历"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              width="85"
              align="center"
              label="毕业时间">
              <template slot-scope="scope">{{ scope.row.graduationTime | formatDate}}</template>
            </el-table-column>
            <el-table-column
              prop="gender"
              label="性别"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="workplace"
              label="工作地点"
              align="center"
              width="70">
            </el-table-column>
            <el-table-column
              prop="job"
              label="岗位"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="workExperience"
              label="工作经验"
              align="center"
              width="100">
            </el-table-column>
            <el-table-column
              prop="wedlock"
              label="婚姻"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="currentSalary"
              label="当前薪资"
              align="center"
              width="70">
            </el-table-column>
            <el-table-column
              prop="expectedSalary"
              label="期望薪资"
              align="center"
              width="70">
            </el-table-column>
            <el-table-column
              prop="recommendClient"
              label="推荐客户"
              align="center"
              width="70">
            </el-table-column>
            <el-table-column
              prop="communicationContent"
              label="沟通内容"
              align="center"
              width="100">
            </el-table-column>
            <el-table-column
              prop="channel"
              label="渠道"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="hr"
              label="HR"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditEmpView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button style="padding: 3px 4px 3px 4px;margin: 2px" type="primary"
                           @click="exportWord(scope.row)" size="mini">导出模板
                </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="exportExecl(scope.row)">导出表格
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
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
                <el-form-item label="姓名:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.name" size="mini" style="width: 150px"
                            placeholder="请输入员工姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="学校:" prop="school">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.school" size="mini" style="width: 150px"
                            placeholder="学校"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="电话号码:" prop="phone">
                  <el-input prefix-icon="el-icon-phone" v-model="emp.phone" size="mini" style="width: 150px"
                            placeholder="电话号码..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="性别:" prop="gender">
                  <el-radio-group v-model="emp.gender">
                    <el-radio label="男">男</el-radio>
                    <el-radio style="margin-left: 15px" label="女">女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="学历:" prop="tiptopDegree">
                  <el-select v-model="emp.tiptopDegree" style="width: 150px" size="mini" placeholder="最高学历">
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
                <el-form-item label="毕业时间:" prop="graduationTime">
                  <el-date-picker
                    v-model="emp.graduationTime"
                    size="mini"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    style="width: 135px"
                    type="date"
                    placeholder="毕业时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="岗位:" prop="job">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.job" size="mini" style="width: 190px"
                            placeholder="岗位"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="工作地点:" prop="workplace">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.workplace" size="mini" style="width: 150px"
                            placeholder="工作地点"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="渠道:" prop="channel">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.channel" size="mini" style="width: 160px"
                            placeholder="渠道..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="工作年限:" prop="workAge">
                  <el-input-number prefix-icon="el-icon-edit" v-model="emp.workAge" style="width: 130px" size="mini"
                                   placeholder="岗位">
                  </el-input-number>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="婚姻状况:" prop="wedlock">
                  <el-radio-group v-model="emp.wedlock">
                    <el-radio label="已婚">已婚</el-radio>
                    <el-radio style="margin-left: 15px" label="未婚">未婚</el-radio>
                    <!--                    <el-radio style="margin-left: 15px" label="离异">离异</el-radio>-->
                  </el-radio-group>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="推荐客户:" prop="recommendClient">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.recommendClient" size="mini" style="width: 150px"
                            placeholder="推荐客户..."></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="期望薪资:" prop="expectedSalary">
                  <el-input-number prefix-icon="el-icon-edit" v-model="emp.expectedSalary" size="mini" :precision="2"
                                   style="width: 130px" placeholder="期望薪资:K"></el-input-number>
                  单位:千
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="当前薪资:" prop="currentSalary">
                  <el-input-number prefix-icon="el-icon-edit" v-model="emp.currentSalary" size="mini" :precision="2"
                                   style="width: 140px" placeholder="期望薪资:K"></el-input-number>
                  单位:千
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="HR:" prop="hr">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.hr" size="mini" style="width: 200px"
                            placeholder="HR..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="面试时间:" prop="interviewTime">
                  <el-date-picker
                    v-model="emp.interviewTime"
                    size="mini"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    style="width: 150px"
                    type="date"
                    placeholder="面试时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="到岗时间:" prop="workTime">
                  <el-date-picker
                    v-model="emp.workTime"
                    size="mini"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    style="width: 135px"
                    type="date"
                    placeholder="到岗时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div>
                <el-form-item label="沟通内容:" prop="communicationContent">
                  <el-input
                    v-model="emp.communicationContent"
                    type="textarea"
                    rows="8"
                    col="24"
                    style="width: 1030px"
                    placeholder="沟通内容">
                  </el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div>
                <el-form-item label="工作经验:" prop="workExperience">
                  <el-input
                    v-model="emp.workExperience"
                    type="textarea"
                    rows="8"
                    col="24"
                    style="width: 1030px"
                    placeholder="工作经验">
                  </el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div>
                <el-form-item label="简  介:" prop="introduction">
                  <el-input
                    v-model="emp.introduction"
                    type="textarea"
                    rows="8"
                    col="24"
                    style="margin-left:20px;width: 1030px"
                    placeholder="简介">
                  </el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div>
                <el-form-item label="项目经验:" prop="projectExperience">
                  <el-input
                    v-model="emp.projectExperience"
                    type="textarea"
                    rows="8"
                    col="24"
                    style="width: 1030px"
                    placeholder="项目经验">
                  </el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>

          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEidt">取 消</el-button>
    <el-button size="mini" type="primary" @click="addEmp('addEmpForm')">确 定</el-button>
  </span>
        </el-dialog>
      </div>
    </el-form>
    <el-form :model="talent" :rules="talentRules" ref="addTalentForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="talentDialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="talentDialogVisible"
          width="40%">
          <el-row>
            <el-col :span="8">
              <div>
                <el-form-item label="推荐客户:" prop="recommendClient">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.recommendClient" size="mini" style="width: 150px"
                            placeholder="请输入推荐客户"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <el-form-item label="推荐日期:" prop="recommendTime">
                  <el-date-picker
                    v-model="talent.recommendTime"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 150px"
                    type="date"
                    placeholder="推荐日期">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <el-form-item label="推荐日期:" prop="recommendTime">
                  <el-date-picker
                    v-model="talent.recommendTime"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 150px"
                    type="date"
                    placeholder="推荐日期">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
          </el-row>

          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="talentCancelEidt">取 消</el-button>
    <el-button size="mini" type="primary" @click="addTalent('addTalentForm')">确 定</el-button>
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
        uploadResumeBtnText: '上传简历',
        beginDateScope: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        talentDialogTitle: '',
        talentDialogVisible:false,
        multipleSelection: [],
        depTextColor: '#c0c4cc',
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
          name: '',
          phone: '',
          school: '',
          tiptopDegree: '',
          graduationTime: '',
          gender: 0,
          workplace: '',
          job: '',
          workAge: 0,
          wedlock: '',
          currentSalary: '',
          expectedSalary: '',
          recommendClient: '',
          channel: '',
          communicationContent: '',
          hr: '',
          interviewTime: '',
          workTime: '',
          introduction: '',
          workExperience: '',
          projectExperience: ''
        },
        talent: {
          id: 0,
          name: "",
          hrId: 0,
          recommendClient: "",
          recommendTime: "",
          job: "",
          workAge: 0,
          progress: "",
        },
        rules: {
          name: [{required: true, message: '必填:姓名', trigger: 'blur'}],
          gender:
            [{required: true, message: '必填:性别', trigger: 'blur'}],
          birthday:
            [{required: true, message: '必填:出生日期', trigger: 'blur'}],
          idCard:
            [{
              required: true,
              message: '必填:身份证号码',
              trigger: 'blur'
            }, {
              pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
              message: '身份证号码格式不正确',
              trigger: 'blur'
            }],
          nationId:
            [{required: true, message: '必填:民族', trigger: 'change'}],
          nativePlace:
            [{required: true, message: '必填:籍贯', trigger: 'blur'}],
          politicId:
            [{required: true, message: '必填:政治面貌', trigger: 'blur'}],
          tiptopDegree:
            [{required: true, message: '必填:学历', trigger: 'blur'}],
          email:
            [{required: true, message: '必填:电子邮箱', trigger: 'blur'}, {
              type: 'email',
              message: '邮箱格式不正确',
              trigger: 'blur'
            }],
          phone:
            [{required: true, message: '必填:电话号码', trigger: 'blur'}],
          graduationTime:
            [{required: true, message: '必填:毕业时间', trigger: 'blur'}],
          workplace:
            [{required: true, message: '必填:工作地点', trigger: 'blur'}],
          job:
            [{required: true, message: '必填:岗位', trigger: 'blur'}],
          communicationContent:
            [{required: true, message: '必填:沟通内容', trigger: 'blur'}],
          school:
            [{required: true, message: '必填:毕业院校', trigger: 'blur'}],
        },
        talentRules: {
          name: [{required: true, message: '必填:姓名', trigger: 'blur'}]
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
        var ids = ""
        for (var i = 0; i < this.multipleSelection.length; i++) {
          ids += this.multipleSelection[i].id + ",";
        }
        ids = ids.substring(0, ids.lastIndexOf(','));
        window.open("/employee/basic/exportEmp?id=" + ids, "_parent");
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
        this.getRequest("/employee/basic/emp?page=" + this.currentPage + "&size=10&name=" + this.keywords).then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            debugger
            var data = resp.data;
            debugger
            _this.emps = data.emps;
            _this.totalCount = data.count;
//            _this.emptyEmpData();
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
              this.putRequest("/employee/basic/emp", this.emp).then(resp => {
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
              this.postRequest("/employee/basic/emp", this.emp).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _
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
      addTalent(formName) {
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
              //添加
              this.tableLoading = true;
              this.postRequest("/employee/basic/updateInterview", this.talent).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _
                  _this.talentDialogVisible = false;
                  _this.emptyEmpData();
                  _this.loadEmps();
                }
              })
          } else {
            return false;
          }
        });
      },
      cancelEidt() {
        this.dialogVisible = false;
        this.emptyEmpData();
      },
      talentCancelEidt() {
        this.talentDialogVisible = false;
        this.emptyTalentData();
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
        console.log(row)
        this.dialogTitle = "编辑员工";
        this.emp = row;

        this.emp.interviewTime = this.formatDate(row.interviewTime);
        this.emp.workTime = this.formatDate(row.workTime);
        this.dialogVisible = true;
      },
      rightClick(row) {
        this.talentDialogTitle="添加到面试信息";
        this.talentDialogVisible=true;
        debugger
        this.talent.name=row.name;
        this.talent.job=row.job;
        this.talent.workAge=row.workAge;
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
        this.emp = {
          name: '',
          gender: '',
          birthday: '',
          idCard: '',
          wedlock: '',
          nationId: '',
          nativePlace: '',
          politicId: '',
          email: '',
          phone: '',
          address: '',
          departmentId: '',
          departmentName: '所属部门...',
          jobLevelId: '',
          posId: '',
          engageForm: '',
          tiptopDegree: '',
          specialty: '',
          school: '',
          beginDate: '',
          workState: '',
          workID: '',
          contractTerm: '',
          conversionTime: '',
          notWorkDate: '',
          beginContract: '',
          endContract: '',
          workAge: ''
        }
      },
      emptyTalentData() {
        this.talent = {
          name:"",
          hrId:0,
          recommendClient:"",
          recommendTime:"",
          job:"",
          workAge:0,
          progress:""
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
</style>
