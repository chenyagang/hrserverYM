<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过员工名搜索员工,记得回车哦..."
            clearable
            @change="nameChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            :disabled="advanceSearchViewVisible"
            @keyup.enter.native="searchTalent"
            prefix-icon="el-icon-search"
            v-model="name">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchTalent">搜索
          </el-button>
          <!--  <el-button slot="reference" type="primary" size="mini" style="margin-left: 5px"
                       @click="showAdvanceSearchView"><i
              class="fa fa-lg" v-bind:class="[advanceSearchViewVisible ? faangledoubleup:faangledoubledown]"
              style="margin-right: 5px"></i>高级搜索
            </el-button>-->
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">

          <el-button type="success" size="mini" @click="exportCount">
            <i class="fa fa-lg fa-level-down" style="margin-right: 5px"></i>导出统计
          </el-button>
        <!--  <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddTalentView">
            添加
          </el-button>-->
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
                  人事:
                  <el-select v-model="talent.hrId" style="width: 130px" size="mini" placeholder="人事">
                    <el-option
                      v-for="item in hrList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="5">
                  状态查询:
                  <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="状态查询">
                    <el-option
                      v-for="item in allStatus"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="5">
                  电话:
                  <el-input style="width: 150px" size="mini"
                            placeholder="电话"
                            prefix-icon="el-icon-search"
                            v-model="talent.phone"/>
                </el-col>
                <el-col :span="5">
                  技术:
                  <el-input style="width: 150px" size="mini"
                            placeholder="技术"
                            prefix-icon="el-icon-search"
                            v-model="talent.technology"/>
                </el-col>
              </el-row>
              <el-row style="margin-top: 10px">
                <el-col :span="5" :offset="4">
                  <el-button size="mini" @click="cancelSearch">取消</el-button>
                  <el-button icon="el-icon-search" type="primary" size="mini" @click="searchTalent">搜索</el-button>
                </el-col>
              </el-row>
            </div>
          </transition>
          <el-table
            :data="talents"
            v-loading="tableLoading"
            :show-overflow-tooltip="overflow"
            stripe
            @row-dblclick="rightClick"
            @selection-change="handleSelectionChange"
            size="mini"
            style="width: 100%;border:1px solid gray;">
            <el-table-column
              type="selection"
              align="center"
              :show-overflow-tooltip="overflow"
              width="30">
            </el-table-column>
            <el-table-column
              width="30"
              type="expand">
              <template slot-scope="scope">
                <el-form label-position="left" inline class="demo-table-expand">
                  <el-form-item label="姓名">
                    <span>{{ scope.row.name }}</span>
                  </el-form-item>
                  <el-form-item label="推荐客户">
                    <span>{{ scope.row.recommendClient }}</span>
                  </el-form-item>
                  <el-form-item label="推荐日期">
                    <span>{{ scope.row.recommendTime }}</span>
                  </el-form-item>
                  <el-form-item label="岗位">
                    <span>{{ scope.row.job }}</span>
                  </el-form-item>
                  <el-form-item label="年限">
                    <span>{{ scope.row.workAge }}</span>
                  </el-form-item>
                  <el-form-item label="进展">
                    <span>{{ scope.row.progress }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="name"
              :show-overflow-tooltip="overflow"
              label="姓名"
              width="90">

            </el-table-column>
            <el-table-column
              align="center"
              prop="recommendClient"
              label="推荐客户"
              :show-overflow-tooltip="overflow"
              width="90">

            </el-table-column>

            <el-table-column
              align="center"
              prop="recommendTime"
              :show-overflow-tooltip="overflow"
              label="推荐日期">
              <template slot-scope="scope">{{ scope.row.recommendTime | formatDate}}</template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="job"
              :show-overflow-tooltip="overflow"
              label="岗位">
            </el-table-column>
            <el-table-column
              align="center"
              prop="workAge"
              :show-overflow-tooltip="overflow"
              label="年限">
            </el-table-column>

            <el-table-column
              align="center"
              prop="progress"
              :show-overflow-tooltip="overflow"
              label="进展">
            </el-table-column>

            <el-table-column
              align="center"
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditTalentView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini" :disabled="'0' == scope.row.showEdit">编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">

            <el-pagination
              background
              :page-size="pageSize"
              :current-page="currentPage"
              @current-change="currentChange"
              layout="prev, pager, next"
              :total="totalCount">
            </el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>


    <el-form :model="talent" :rules="rules" ref="talentForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :before-close="dialogClose"
          :visible.sync="dialogVisible"
          width="77%">
          <el-row>
            <el-col :span="8">
              <div>
                <el-form-item label="姓名:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.name" size="mini" style="width: 200px"
                            placeholder="请输入姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <el-form-item label="推荐客户:" prop="recommendClient">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.recommendClient" size="mini" style="width: 200px"
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
                <el-form-item label="岗位:" prop="job">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.job" size="mini" style="width: 200px"
                            placeholder="请输入岗位"></el-input>
                </el-form-item>
              </div>
            </el-col>

            <el-col :span="8">
              <div>
                <el-form-item label="年限:" prop="workAge">
                  <el-input-number prefix-icon="el-icon-edit" v-model="talent.workAge" style="width: 130px" size="mini"
                                   placeholder="工作年限">
                  </el-input-number>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <el-form-item label="进展:" prop="progress">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.progress" size="mini" style="width: 150px"
                            placeholder="请输入进展"></el-input>

                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
            <el-button size="mini" @click="cancelEdit">取 消</el-button>
            <el-button size="mini" type="primary" @click="updateTalent('talentForm')">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </el-form>
    <el-form :model="induction" :rules="inductionRules" ref="induction" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="inductionDialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="inductionDialogVisible"
          width="70%">
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="入职时间:" prop="inductionDate">
                  <el-date-picker
                    @change="inductionOnChange"
                    v-model="induction.inductionDate"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 135px"
                    type="date"
                    placeholder="入职时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="技术模块:" prop="technologyModule">
                  <el-input prefix-icon="el-icon-edit" v-model="induction.technologyModule" size="mini"
                            style="width: 150px"
                            placeholder="请输入技术模块"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="员工类型:" prop="category">
                  <el-select v-model="induction.category" style="width: 150px" size="mini" placeholder="员工类型">
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
                <el-form-item label="转正时间:" prop="positiveTime">
                  <el-date-picker
                    v-model="induction.positiveTime"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    style="width: 135px"
                    type="date"
                    placeholder="转正时间">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="正式薪资:" prop="salary">
                  <el-input-number prefix-icon="el-icon-edit" v-model="induction.salary" size="mini" :precision="2"
                                   style="width: 130px" placeholder="正式薪资:K"></el-input-number>
                  单位:千
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="试用期薪资:" prop="probationSalary">
                  <el-input-number prefix-icon="el-icon-edit" v-model="induction.probationSalary" size="mini"
                                   :precision="2"
                                   style="width: 130px" placeholder="试用期薪资:K"></el-input-number>
                  单位:千
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="客户报价:" prop="cstomerQuotation">
                  <el-input-number prefix-icon="el-icon-edit" v-model="induction.cstomerQuotation" size="mini"
                                   :precision="2"
                                   style="width: 130px" placeholder="客户报价"></el-input-number>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="定级:" prop="grading">
                  <el-input prefix-icon="el-icon-edit" v-model="induction.grading" size="mini" style="width: 150px"
                            placeholder="定级..."></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="身份证号:" prop="idNumber">
                  <el-input prefix-icon="el-icon-edit" v-model="induction.idNumber" size="mini" style="width: 150px"
                            placeholder="身份证号..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="银行卡号:" prop="bankCardNumber">
                  <el-input prefix-icon="el-icon-edit" v-model="induction.bankCardNumber" size="mini"
                            style="width: 150px"
                            placeholder="银行卡号..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="入户银行:" prop="bank">
                  <el-input prefix-icon="el-icon-edit" v-model="induction.bank" size="mini" style="width: 150px"
                            placeholder="入户银行..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="公司:" prop="company">
                  <el-input prefix-icon="el-icon-edit" v-model="induction.company" size="mini" style="width: 150px"
                            placeholder="公司..."></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="备注:" prop="note">
                  <el-input prefix-icon="el-icon-edit" v-model="induction.note" size="mini" style="width: 150px"
                            placeholder="备注..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="入项:" prop="item">
                  <el-input prefix-icon="el-icon-edit" v-model="induction.item" size="mini" style="width: 150px"
                            placeholder="入项..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="入职地点:" prop="item">
                  <el-input prefix-icon="el-icon-edit" v-model="induction.inductionPlace" size="mini"
                            style="width: 150px"
                            placeholder="入职地点..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="部门:" prop="department">
                  <el-select v-model="induction.department" style="width: 150px" size="mini" placeholder="部门">
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
          </el-row>
          <span slot="footer" class="dialog-footer">
              <el-button size="mini" type="primary" @click="addInduction('induction')">确 定</el-button>
         </span>
        </el-dialog>
      </div>
    </el-form>
    <el-dialog
      :title="statusDialogTitle"
      :before-close="dialogClose"
      style="padding: 0px;"
      :close-on-click-modal="false"
      :visible.sync="statusDialogVisible"
      width="77%">

      <template>
        <div v-if="talent.status == 0 || talent.status == 2">
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="预约" v-if="talent.status == 0"
                     @change="updateStatus">
            <el-option
              v-for="item in status.appoint"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <label v-if="talent.status == 2">预约失败</label>
        </div>
        <div v-if="talent.status == 1 || talent.status == 4">
          <label>预约成功</label>->
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="是否应约内面"
                     v-if="talent.status == 1" @change="updateStatus">
            <el-option
              v-for="item in status.isInside"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <label v-if="talent.status == 4">未参加内面</label>
        </div>

        <div v-if="talent.status == 3 || talent.status == 6">
          <label>预约成功</label>->
          <label>参加内面</label>->
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="内面结果"
                     v-if="talent.status == 3" @change="updateStatus">
            <el-option
              v-for="item in status.inside"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <label v-if="talent.status == 6">内面失败</label>
        </div>


        <div v-if="talent.status == 5 || talent.status == 8">
          <label>预约成功</label>->
          <label>参加内面</label>->
          <label>内面成功</label>->
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="是否应约客面"
                     v-if="talent.status == 5" @change="updateStatus">
            <el-option
              v-for="item in status.isCustomer"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <label v-if="talent.status == 8">未参加客面</label>
        </div>


        <div v-if="talent.status == 7 || talent.status == 10">
          <label>预约成功</label>->
          <label>参加内面</label>->
          <label>内面成功</label>->
          <label>参加客面</label>->
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="客面结果"
                     v-if="talent.status == 7" @change="updateStatus">
            <el-option
              v-for="item in status.customer"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <label v-if="talent.status == 10">客面失败</label>
        </div>
        <div v-if="talent.status == 9 || talent.status == 12">
          <label>预约成功</label>->
          <label>参加内面</label>->
          <label>内面成功</label>->
          <label>参加客面</label>->
          <label>客面成功</label>->
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="offer结果"
                     v-if="talent.status == 9" @change="updateStatus">
            <el-option
              v-for="item in status.offer"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <label v-if="talent.status == 12">offer失败</label>
        </div>
        <div v-if="talent.status == 11 || talent.status == 14">
          <label>预约成功</label>->
          <label>参加内面</label>->
          <label>内面成功</label>->
          <label>参加客面</label>->
          <label>客面成功</label>->
          <label>offer成功</label>->
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="入职结果"
                     v-if="talent.status == 11" @change="updateStatus">
            <el-option
              v-for="item in status.entry"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <label v-if="talent.status == 14">入职失败</label>
        </div>


        <div v-if="talent.status == 13">
          <label>预约成功</label>->
          <label>参加内面</label>->
          <label>内面成功</label>->
          <label>参加客面</label>->
          <label>客面成功</label>->
          <label>offer成功</label>->
          <label>入职成功</label>->
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="是否完成" @change="updateStatus">
            <el-option
              v-for="item in status.finish"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </div>

        <div v-if="talent.status > 14">
          <label>预约成功</label>->
          <label>参加内面</label>->
          <label>内面成功</label>->
          <label>参加客面</label>->
          <label>客面成功</label>->
          <label>offer成功</label>->
          <label>入职成功</label>->
          <label v-if="talent.status == 15">已完成</label>
          <label v-if="talent.status == 16">未完成</label>
        </div>

        <div style="margin-top: 20px;" v-if="talent.status == 11 || talent.status > 12">
          <el-input prefix-icon="el-icon-edit" v-model="talent.level" size="mini" style="width: 150px"
                    placeholder="请输入定级"></el-input>
          <el-input prefix-icon="el-icon-edit" v-model="talent.profits" size="mini" style="width: 150px"
                    placeholder="请输入利润"></el-input>
          <el-date-picker
            v-model="talent.entryDate"
            type="date"
            style="width: 150px"
            class="el-input--mini"
            default-time="09:00:00"
            value-format="yyyy-MM-dd"
            placeholder="请选择到岗时间">
          </el-date-picker>
          <el-input prefix-icon="el-icon-edit" v-model="talent.entryMark" size="mini" style="width: 150px"
                    placeholder="请输入备注"></el-input>
          <el-button size="mini" type="primary" @click="updateEntryInfo()">确 定</el-button>
        </div>

        <span slot="footer" class="dialog-footer">
             <el-button size="mini" @click="cancelStatus">关 闭</el-button>
          <!-- <el-button size="mini" type="primary" @click="updateStatus()">确 定</el-button>-->
          </span>

      </template>

    </el-dialog>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        radio: '1',
        talents: [],
        name: '',
        hrName: '',
        fileUploadBtnText: '导入数据',
        recommendTime: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        statusDialogTitle: '',
        inductionDialogTitle: '添加到入职信息',
        inductionDialogVisible: false,
        depTextColor: '#c0c4cc',
        columnShow: false,
        multipleSelection: [],
        hrList: [],
        totalCount: -1,
        currentPage: 1,
        pageSize: 10,
        oldStatus: null,
        level: null,  //定级
        profits: null, //利润
        entryDate: null, //到岗时间
        entryMark: null, //备注
        //appointmentList: [{id: 1, name: '否'}, {id: 2, name: '是'}],
        status: {
          appoint: [{id: 0, name: '预约结果'}, {id: 1, name: '预约成功'}, {id: 2, name: '预约失败'}],
          isInside: [{id: 1, name: '是否应约内面'}, {id: 3, name: '参加内面'}, {id: 4, name: '未参加内面'}],
          inside: [{id: 3, name: '内面结果'}, {id: 5, name: '内面成功'}, {id: 6, name: '内面失败'}],
          isCustomer: [{id: 5, name: '是否应约客面'}, {id: 7, name: '参加客面'}, {id: 8, name: '未参加客面'}],
          customer: [{id: 7, name: '客面结果'}, {id: 9, name: '客面成功'}, {id: 10, name: '客面失败'}],
          offer: [{id: 9, name: 'offer结果'}, {id: 11, name: 'offer成功'}, {id: 12, name: 'offer失败'}],
          entry: [{id: 11, name: '入职结果'}, {id: 13, name: '入职成功'}, {id: 14, name: '入职失败'}],
          finish: [{id: 13, name: '是否完成'}, {id: 15, name: '已完成'}, {id: 16, name: '未完成'}]
        },
        allStatus: [{id: 1, name: '预约成功'}, {id: 2, name: '预约失败'}, {id: 3, name: '参加内面'}, {id: 4, name: '未参加内面'}, {
          id: 5,
          name: '内面成功'
        }, {id: 6, name: '内面失败'},
          {id: 7, name: '参加客面'}, {id: 8, name: '未参加客面'}, {id: 9, name: '客面成功'}, {id: 10, name: '客面失败'}, {
            id: 11,
            name: 'offer成功'
          }, {id: 12, name: 'offer失败'},
          {id: 13, name: '入职成功'}, {id: 14, name: '入职失败'}, {id: 15, name: '已完成'}, {id: 16, name: '未完成'}],
        department: [{id: "研发", name: '研发'}, {id: "人事", name: '人事'}, {id: "测试", name: '测试'}],
        category: [{id: "正式", name: '正式'}, {id: "外协", name: '外协'}, {id: "兼职", name: '兼职'}, {id: "小微", name: '小微'}],
        dialogVisible: false,
        tableLoading: false,
        advanceSearchViewVisible: false,
        showOrHidePop: false,
        showOrHidePop2: false,
        statusDialogVisible: false,
        talent: {
          id: null,
          hrId: null,
          employeeId: null,
          name: null,
          recommendClient: null,
          recommendTime: null,
          job: null,
          workAge: null,

          progress: null
        },
        induction: {
          talentpoolId: 0,
          hrId: 0,
          name: null,
          gender: null,
          inductionDate: null,
          technologyModule: null,
          department: null,
          inductionPlace: null,
          graduationTime: null,
          positiveTime: null,
          degree: null,
          workAge: 0,
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
        rules: {
          name: [{required: true, message: '必填:姓名', trigger: 'blur'}],
          phone: [{required: true, message: '必填:电话', trigger: 'blur'}]
        },
        inductionRules: {
          inductionDate: [{required: true, message: '必填:入职时间', trigger: 'blur'}],
          technologyModule: [{required: true, message: '必填:技术模块', trigger: 'blur'}],
          category: [{required: true, message: '必填:员工类别', trigger: 'blur'}],
          positiveTime: [{required: true, message: '必填:转正时间', trigger: 'blur'}],
          department: [{required: true, message: '必填:部门', trigger: 'blur'}],
          salary: [{required: true, message: '必填:正式薪资', trigger: 'blur'}],
          probationSalary: [{required: true, message: '必填:姓名', trigger: 'blur'}],
          cstomerQuotation: [{required: true, message: '必填:客户报价', trigger: 'blur'}],

        },
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
        countStart: null,
        countEnd: null
      };
    },
    mounted: function () {
      this.initData();
      this.loadTalent();
    },
    methods: {
      loadTalent: function () {
        var _this = this;
        this.tableLoading = true;

        var param = "?pageNo=" + this.currentPage + "&pageSize=" + this.pageSize + "&name=" + _this.name;
        if (this.talent.hrId != null) {
          param += "&hrId=" + this.talent.hrId;
        }
        if (this.talent.status != null && this.talent.status != 0) {
          param += "&status=" + this.talent.status;
        }
        if (this.talent.phone != null) {
          param += "&phone=" + this.talent.phone;
        }
        if (this.talent.technology != null) {
          param += "&technology=" + this.talent.technology;
        }
       this.getRequest("/talent/basic/getTalentPage" + param).then(resp => {
       //  this.getRequest("/employee/basic/empByPageAndHrId" + param).then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.talents = data.data.talents;
            _this.totalCount = data.data.count;
          }
        })
      },
      searchTalent: function () {
              var _this = this;
              this.tableLoading = true;

              var param = "?pageNo=" + this.currentPage + "&pageSize=" + this.pageSize + "&name=" + _this.name + "&hrFlag= NO";
              if (this.talent.hrId != null) {
                param += "&hrId=" + this.talent.hrId;
              }
              if (this.talent.status != null && this.talent.status != 0) {
                param += "&status=" + this.talent.status;
              }
              if (this.talent.phone != null) {
                param += "&phone=" + this.talent.phone;
              }
              if (this.talent.technology != null) {
                param += "&technology=" + this.talent.technology;
              }

             this.getRequest("/talent/basic/getTalentPage" + param).then(resp => {
             //  this.getRequest("/employee/basic/empByPageAndHrId" + param).then(resp => {
                this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.talents = data.data.talents;
                  _this.totalCount = data.data.count;
                }
              })
            },
       currentChange(currentChange) {
        this.currentPage = currentChange;
        this.loadTalent();
      }, searchTalent() {
        this.loadTalent();
      }, nameChange() {
        this.loadTalent();
      },
        talentCancelEidt() {
               this.inductionDialogVisible = false;
                //this.loadTalent();
         },
       updateStatus() {
        this.postRequest("/talent/basic/updateTalent", {
          "id": this.talent.id,
          "status": this.talent.status
        }).then(resp => {
          if (resp && resp.status == 200) {
          this.statusDialogVisible = false;
            var data = resp.data;
            if (data.resultCode == 1) {
              this.$message({type: resp.status, message: "流转成功!"});
            } else if (data.resultCode == 10001) {
              this.$message({type: resp.status, message: "一周内不能编辑!"});
              this.talent.status = this.oldStatus;
              this.statusDialogVisible = false;
            }
          }
        });
      }, showAdvanceSearchView() {
        this.emptyTalentData();
        this.advanceSearchViewVisible = !this.advanceSearchViewVisible;
        this.name = '';
        if (!this.advanceSearchViewVisible) {
          this.beginDateScope = '';
          this.loadTalent();
        }
      },
      rightClick(row) {
        debugger
        this.inductionDialogTitle = "添加到入职信息";
        this.inductionDialogVisible = true;
        debugger
        this.induction.talentpoolId = row.id;

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
                _this.inductionDialogVisible = false;
                _this.emptyEmpData();
                _this.loadEmps();
              }
            })
          } else {
            return false
          }
        });
      },
      emptyTalentData() {
        this.talent = {
          id: null,
          hrId: null,
          name: null,
          recommendClient: null,
          recommendTime: null,
          job: null,
          workAge: null,
          progress: null
        }
      },
      inductionOnChange() {
        debugger
        this.induction.positiveTime = this.$moment(this.induction.inductionDate).add(3, "months").format("YYYY-MM-DD")
      },
      cancelSearch() {
        this.advanceSearchViewVisible = false;
        this.emptyTalentData();
        this.beginDateScope = '';
        this.loadTalent();
      }, initData() {
        var _this = this;
        this.getRequest("/talent/basic/basicData").then(resp => {
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.hrList = data.data.hrList;
          }
        })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      fileUploadSuccess(response, file, fileList) {
        if (response.resultCode == 1) {
          this.$message({type: response.status, message: "导入成功"});
        }
        this.loadTalent();
        this.fileUploadBtnText = '导入数据';
      },
      fileUploadError(err, file, fileList) {
        this.$message({type: 'error', message: "导入失败!"});
        this.fileUploadBtnText = '导入数据';
      },
      beforeFileUpload(file) {
        this.fileUploadBtnText = '正在导入';
      },
      showEditTalentView(row) {
        this.dialogTitle = "编辑";
        this.talent = row;
        this.dialogVisible = true;
      }, updateTalent(formName) {
        this.tableLoading = true;
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var obj = new Object();

            for (var o in this.talent) {
              if (this.talent[o]) {
                obj[o] = this.talent[o];
              }
            }

            if (this.talent.id) {
              //更新
              this.postRequest("/talent/basic/updateTalent", obj).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  // this.emptyTalentData();
                  var data = resp.data;
                  if (data.resultCode == 1) {
                    this.loadTalent();
                    this.$message({type: resp.status, message: "编辑成功!"});

                  } else if (data.resultCode == 10001) {
                    this.$message({type: resp.status, message: "一周内不能编辑!"});
                  }
                  _this.dialogVisible = false;
                }
              })
            } else {
              //添加
              this.postRequest("/talent/basic/add", obj).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  if (data.resultCode == 1) {
                    this.$message({type: resp.status, message: "添加成功!"});
                    this.loadTalent();
                  }
                  _this.dialogVisible = false;
                }
              })
            }
          }
        });
        _this.emptyTalentData();
        _this.loadTalent();
      }, exportTalent() {
        var _this = this;
        var url = "/talent/basic/exportTalent";
        var param = "?name=" + _this.name;
        if (this.talent.hrId != null) {
          param += "&hrId=" + this.talent.hrId;
        }
        if (this.talent.status != null) {
          param += "&status=" + this.talent.status;
        }
        if (this.talent.phone != null) {
          param += "&phone=" + this.talent.phone;
        }
        if (this.talent.technology != null) {
          param += "&technology=" + this.talent.technology;
        }
        window.open(url + param, "_parent");
      }, showStatusDialog(row) {
        this.statusDialogTitle = "状态流转";
        this.statusDialogVisible = true;
        this.talent = row;
        this.oldStatus = this.talent.status;
      }, cancelEdit() {
        this.dialogVisible = false;
        this.emptyTalentData();
      }, cancelStatus() {
        this.statusDialogVisible = false;
        this.emptyTalentData();
      }, showAddTalentView() {
        this.emptyTalentData();
        this.dialogTitle = "添加";
        this.dialogVisible = true;
      }, exportCount() {
        var ids = ""
        for (var i = 0; i < this.multipleSelection.length; i++) {
          ids += this.multipleSelection[i].id + ",";
        }
        ids = ids.substring(0, ids.lastIndexOf(','));
        var url = "/talent/basic/exportCount?id=" + ids;
        window.open(url, "_parent");
      }, updateEntryInfo() {
        var _this = this;
        var obj = new Object();
        obj['id'] = this.talent.id;
        if (this.talent.level) {
          obj['level'] = this.talent.level;
        }
        if (this.talent.profits) {
          obj['profits'] = this.talent.profits;
        }
        if (this.talent.entryDate) {
          obj['entryDate'] = this.talent.entryDate;
        }
        if (this.talent.entryMark) {
          obj['entryMark'] = this.talent.entryMark;
        }

        this.postRequest("/talent/basic/updateTalent", obj).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            if (data.resultCode == 1) {
              this.$message({type: resp.status, message: "编辑成功!"});
            } else if (data.resultCode == 10001) {
              this.$message({type: resp.status, message: "一周内不能编辑!"});
            }
          }
        })
      }, dialogClose(done) {
        this.emptyTalentData();
        done();
      }
    },
    emptyEmpData() {
      this.induction = {
        talentpoolId: 0,
        hrId: 0,
        name: null,
        gender: null,
        inductionDate: null,
        technologyModule: null,
        department: null,
        inductionPlace: null,
        graduationTime: null,
        positiveTime: null,
        degree: null,
        workAge: 0,
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
    }
  }
</script>

<style>
  .el-table .cell {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    white-space: nowrap !important;
    word-break: break-all;
    line-height: 23px;
    text-overflow: ellipsis;
  }

  .el-table .cell div {
    display: block;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
  }

  .is-leaf {
    font-family: STHeiti Light;
    background-color: #abdae4 !important;
  }

  .el-table td, .el-table th.is-leaf {
    border-bottom: 1px solid gray;
  }

  .el-table--border td, .el-table--border th, .el-table__body-wrapper .el-table--border.is-scrolling-left ~ .el-table__fixed {
    border-right: 1px solid gray;
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
    width: 50%;
  }
</style>
