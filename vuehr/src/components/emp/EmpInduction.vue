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
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-upload
            :show-file-list="false"
            accept="application/vnd.ms-excel"
            action="/employee/basic/importEmp"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError" :disabled="fileUploadBtnText=='正在导入'"
            :before-upload="beforeFileUpload" style="display: inline">
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
              prop="gender"
              label="性别"
              align="center"
              width="100">
            </el-table-column>
            <el-table-column
              prop="inductionDate"
              label="入职时间"
              align="center"
              width="100">

              <template slot-scope="scope">{{ scope.row.inductionDate | formatDate}}</template>
            </el-table-column>
            <el-table-column
              prop="technologyModule"
              label="技术模块"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="category"
              label="员工类别"
              align="center"
              width="70">
            </el-table-column>
            <el-table-column
              prop="customer"
              label="对应客户"
              align="customer"
              width="50">
            </el-table-column>
            <el-table-column
              prop="graduationTime"
              label="毕业时间"
              align="center"
              width="100">
              <template slot-scope="scope">{{ scope.row.graduationTime | formatDate}}</template>
            </el-table-column>
            <el-table-column
              prop="positiveTime"
              label="转正时间"
              align="center"
              width="50">
              <template slot-scope="scope">{{ scope.row.positiveTime | formatDate}}</template>
            </el-table-column>
            <el-table-column
              prop="degree"
              label="学历"
              align="center"
              width="70">
            </el-table-column>
            <el-table-column
              prop="workAge"
              label="工作年限"
              align="center"
              width="70">
            </el-table-column>

            <el-table-column
              prop="phone"
              label="联系方式"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="salary"
              label="薪资"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="probationSalary"
              label="试用期薪资"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="cstomerQuotation"
              label="客户报价"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="grading"
              label="定级"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="idNumber"
              label="身份证号"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="bankCardNumber"
              label="银行卡号"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="bank"
              label="入户银行"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="channel"
              label="渠道"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="company"
              label="公司"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="note"
              label="备注"
              align="center"
              width="50">
            </el-table-column>
            <el-table-column
              prop="item"
              label="入项"
              align="center"
              width="50">
            </el-table-column>

            <el-table-column
              fixed="right"
              align="center"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditEmpView(scope.row)"
                           style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div id="menu">
            <div class="menu" v-for="(item,index) in menus" :key="index" @click.stop="infoClick(index)">{{item}}</div>
          </div>

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
                <el-form-item label="性别:" prop="gender">
                  <el-radio-group v-model="emp.gender">
                    <el-radio label="男">男</el-radio>
                    <el-radio style="margin-left: 15px" label="女">女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>

                <el-form-item label="入职时间:" prop="inductionDate">
                  <el-date-picker
                    v-model="emp.inductionDate"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 135px"
                    type="date"
                    placeholder="入职时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="技术模块:" prop="technologyModule">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.technologyModule" size="mini" style="width: 150px"
                            placeholder="请输入技术模块"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="部门:" prop="department">
                  <el-select v-model="emp.department" style="width: 150px" size="mini" placeholder="部门">
                    <el-option
                      v-for="item in department"
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
                <el-form-item label="工作地点:" prop="inductionPlace">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.inductionPlace" size="mini" style="width: 150px"
                            placeholder="请输入工作地点"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="员工类别:" prop="category">
                  <el-select v-model="emp.category" style="width: 150px" size="mini" placeholder="员工类别">
                    <el-option
                      v-for="item in category"
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
                <el-form-item label="客户:" prop="customer">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.customer" size="mini" style="width: 150px"
                            placeholder="客户"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="转正时间:" prop="positiveTime">
                  <el-date-picker
                    v-model="emp.positiveTime"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 135px"
                    type="date"
                    placeholder="转正时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="毕业时间:" prop="graduationTime">
                  <el-date-picker
                    v-model="emp.graduationTime"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 135px"
                    type="date"
                    placeholder="到岗时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="学历:" prop="degree">
                  <el-select v-model="emp.degree" style="width: 150px" size="mini" placeholder="最高学历">
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
                <el-form-item label="电话号码:" prop="phone">
                  <el-input prefix-icon="el-icon-phone" v-model="emp.phone" size="mini" style="width: 150px"
                            placeholder="电话号码..."></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="正式薪资:" prop="salary">
                  <el-input-number prefix-icon="el-icon-edit" v-model="emp.salary" size="mini" :precision="2"
                                   style="width: 130px" placeholder="正式薪资:K"></el-input-number>
                  单位:千
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="试用期薪资:" prop="probationSalary">
                  <el-input-number prefix-icon="el-icon-edit" v-model="emp.probationSalary" size="mini" :precision="2"
                                   style="width: 140px" placeholder="试用期薪资:K"></el-input-number>
                  单位:千
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="客户报价:" prop="cstomerQuotation">
                  <el-input-number prefix-icon="el-icon-edit" v-model="emp.cstomerQuotation" size="mini" :precision="2"
                                   style="width: 130px" placeholder="客户报价"></el-input-number>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="定级:" prop="grading">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.grading" size="mini" style="width: 200px"
                            placeholder="定级..."></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="身份证号:" prop="idNumber">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.grading" size="mini" style="width: 200px"
                            placeholder="身份证号..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="银行卡号:" prop="bankCardNumber">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.bankCardNumber" size="mini" style="width: 200px"
                            placeholder="银行卡号..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="入户银行:" prop="bank">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.bank" size="mini" style="width: 200px"
                            placeholder="入户银行..."></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="公司:" prop="company">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.company" size="mini" style="width: 200px"
                            placeholder="公司..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="备注:" prop="note">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.note" size="mini" style="width: 200px"
                            placeholder="备注..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="入项:" prop="item">
                  <el-input prefix-icon="el-icon-edit" v-model="emp.item" size="mini" style="width: 200px"
                            placeholder="入项..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="工作年限:" prop="workAge">
                  <el-input-number prefix-icon="el-icon-edit" v-model="emp.workAge" size="mini" style="width: 150px"
                                   placeholder="工作年限..."></el-input-number>
                </el-form-item>
              </div>
            </el-col>
          </el-row>

          <span slot="footer" class="dialog-footer">
          <el-button size="mini" @click="cancelEidt">取 消</el-button>
          <el-button size="mini" type="primary" @click="addInduction('addEmpForm')">确 定</el-button>
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
        menus: ['加入面试', '方案分析', '方案存库', '清除方案'],
        emps: [],
        keywords: '',
        fileUploadBtnText: '导入数据',
        uploadResumeBtnText: '上传简历',
        beginDateScope: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        talentDialogTitle: '',
        talentDialogVisible: false,
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
        department: [{id: "研发", name: '研发'}, {id: "人事", name: '人事'}, {id: "测试", name: '测试'}],
        category: [{id: "正式", name: '正式'}, {id: "外协", name: '外协'}, {id: "兼职", name: '兼职'}, {id: "小微", name: '小微'}],
        deps: [],
        mianshiList: [{id: 2, name: '初中'}, {id: 1, name: '加入面试'}],
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
          id: null,
          hrId:0,
          name: null,
          gender: null,
          inductionDate: null,
          technologyModule: null,
          department: null,
          inductionPlace: null,
          graduationTime: null,
          positiveTime: null,
          degree: null,
          workAge: null,
          phone: null,
          salary: null,
          probationSalary: null,
          cstomerQuotation: null,
          grading: null,
          idNumber: null,
          bankCardNumber: null,
          bank: null,
          channel: null,
          company: null,
          note: null,
          item: null,
        },
        hr: {},
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
        rules: {},
        talentRules: {
          name: [{required: true, message: '必填:姓名', trigger: 'blur'}]
        }
      }
        ;
    },
    mounted: function () {
      this.initData();

      this.loadEmps();
    },
    methods: {

      // 自定义菜单的点击事件
      infoClick(index) {
        this.$alert('当前table的下标为' + this.currentRowIndex, '你点击了自定义菜单的' + this.menus[index] + '功能', {
          confirmButtonText: '确定',
          callback: action => {
            var menu = document.querySelector("#menu");
            menu.style.display = 'none';
          }
        });
      },

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
        window.open("/employee/basic/exportInduction?id=" + ids, "_parent");
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
      updateShowInterview() {
        this.$confirm('此操作将加入[' + this.multipleSelection.length + ']条数据到面试信息中, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var ids = '';
          for (var i = 0; i < this.multipleSelection.length; i++) {
            ids += this.multipleSelection[i].id + ",";
            //     open(this.multipleSelection[i]);
          }

          this.doUpdateShowInterview(ids);
        }).catch(() => {
        });
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
      //转让权限
      transferAuthority(row) {
        this.$confirm('此操作将会将面试者转移到当前用户下, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.tableLoading = true;
          var _this = this;
          this.emp = row;
          debugger
          this.postRequest("/employee/basic/transferAuthority?id=" + row.id).then(resp => {
            _this.tableLoading = false;
            if (resp && resp.status == 200) {
              var data = resp.data;
              _this.loadEmps();
            }
          })
        }).catch(() => {
        });

      },
      doUpdateShowInterview(ids) {
        this.tableLoading = true;
        var _this = this;
        this.putRequest("/talent/basic/updateInterview/", {ids: ids}).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.loadEmps();
          }
        })
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
        this.getRequest("/employee/basic/getInductionByPage?page=" + this.currentPage + "&size=10&name=" + this.keywords + "&hrFlag= YES").then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
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
              this.emp.hrId=0;
              this.putRequest("/employee/basic/updateInduction", this.emp).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  debugger
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyEmpData();
                  _this.loadEmps();
                }
              })
            } else {
              //添加
              this.tableLoading = true;
              this.emp.hrId=0;
              this.postRequest("/employee/basic/insertInduction", this.emp).then(resp => {
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
      addInduction(formName) {
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
              this.postRequest("/employee/basic/addInduction", this.induction).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  debugger
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyEmpData();
                  _this.loadEmps();
                }
              })
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
      initData() {
        var _this = this;
        this.getRequest("/system/hr/getCurrentUser").then(resp => {
          if (resp && resp.status == 200) {
            debugger
            this.hr = resp.data;
          }
        })
      },
      showEditEmpView(row) {
        console.log(row)
        this.dialogTitle = "编辑员工";
        debugger
        this.emp = row;

        this.dialogVisible = true;
      },
      rightClick(row) {
        this.talentDialogTitle = "添加到面试信息";
        this.talentDialogVisible = true;
        debugger
        this.talent.name = row.name;
        this.talent.job = row.job;
        this.talent.workAge = row.workAge;
      },
      showEditEmpViewMianshi(row) {
        console.log(row)
        this.dialogTitle = "编辑员工信息";
        this.emp = row;
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
        this.emp = {
          id: null,
          hrId:0,
          name: null,
          gender: null,
          inductionDate: null,
          technologyModule: null,
          department: null,
          inductionPlace: null,
          graduationTime: null,
          positiveTime: null,
          degree: null,
          workAge: null,
          phone: null,
          salary: null,
          probationSalary: null,
          cstomerQuotation: null,
          grading: null,
          idNumber: null,
          bankCardNumber: null,
          bank: null,
          channel: null,
          company: null,
          note: null,
          item: null,
        }
      },
      emptyTalentData() {
        this.talent = {
          name: "",
          hrId: 0,
          recommendClient: "",
          recommendTime: "",
          job: "",
          workAge: 0,
          progress: ""
        }
      }
    }
  };
</script>
<style>

  #menu {
    width: 120px;
    height: 100px;
    overflow: hidden; /*隐藏溢出的元素*/
    box-shadow: 0 1px 1px #888, 1px 0 1px #ccc;
    position: absolute;
    display: none;
    background: #ffffff;
    z-index: 10;
  }

  .menu {
    width: 125px;
    height: 25px;
    line-height: 25px;
    text-indent: 10px;
    cursor: pointer;
  }

  .menu:hover {
    color: deeppink;
    text-decoration: underline;
  }


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
