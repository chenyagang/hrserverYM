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
          <el-button slot="reference" type="primary" size="mini" style="margin-left: 5px"
                     @click="showAdvanceSearchView"><i
            class="fa fa-lg" v-bind:class="[advanceSearchViewVisible ? faangledoubleup:faangledoubledown]"
            style="margin-right: 5px"></i>高级搜索
          </el-button>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <!--
          <el-upload
            :show-file-list="false"
            accept="application/vnd.ms-excel"
            action="/talent/basic/importTalent"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError" :disabled="fileUploadBtnText=='正在导入'"
            :before-upload="beforeFileUpload" style="display: inline">
            <el-button size="mini" type="success" :loading="fileUploadBtnText=='正在导入'">
              <i class="fa fa-lg fa-level-up" style="margin-right: 5px"></i>{{fileUploadBtnText}}
            </el-button>
          </el-upload>-->

            <el-date-picker
              v-model="countStart"
              align="right"
              type="datetime"
              placeholder="统计开始日期"
              class="el-input--mini"
              default-time="['00:00:00']"
              :picker-options="pickerOptions">
            </el-date-picker>
          <el-date-picker
            v-model="countEnd"
            align="right"
            type="datetime"
            placeholder="统计结束日期"
            class="el-input--mini"
            default-time="['23:59:59']"
            :picker-options="pickerOptions">
          </el-date-picker>

          <el-button type="success" size="mini" @click="exportCount">
            <i class="fa fa-lg fa-level-down" style="margin-right: 5px"></i>导出统计
          </el-button>
          <el-button type="success" size="mini" @click="exportTalent">
            <i class="fa fa-lg fa-level-down" style="margin-right: 5px"></i>导出数据
          </el-button>
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddTalentView">
            添加
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
            border
            stripe
            @selection-change="handleSelectionChange"
            size="mini"
            style="width: 100%;border:1px solid gray;">
            <!--<el-table-column
              type="selection"
              align="center"
              width="30">
            </el-table-column>-->
            <el-table-column
              align="center"
              prop="name"
              fixed
              label="姓名"
              width="90">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.name">
                  <div slot>{{scope.row.name}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="phone"
              width="100px"
              label="电话">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.phone">
                  <div slot>{{scope.row.phone}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="technology"
              width="150px"
              label="技术">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.technology">
                  <div slot>{{scope.row.technology}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="workCity"
              width="100px"
              label="现工作地点">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.workCity">
                  <div slot>{{scope.row.workCity}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              width="100px"
              prop="lastCompany"
              label="最近在职公司">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.lastCompany">
                  <div slot>{{scope.row.lastCompany}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="school"
              label="毕业学校">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.school">
                  <div slot>{{scope.row.school}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="graduation"
              label="毕业时间">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.graduation">
                  <div slot>{{scope.row.graduation}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="major"
              label="专业">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.major">
                  <div slot>{{scope.row.major}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="education"
              label="学历">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.education">
                  <div slot>{{scope.row.education}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="lastSalary"
              label="先薪资">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.lastSalary">
                  <div slot>{{scope.row.lastSalary}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="hopeSalary"
              label="期望薪资">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.hopeSalary">
                  <div slot>{{scope.row.hopeSalary}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <!--
            <el-table-column
              prop="appointment"
              align="center"
              label="是否预约"
              width="200px">
              <template slot-scope="scope">
                <el-switch
                  style="display: block"
                  @change="appointmentChange(scope.row.appointment,scope.row.id)"
                  v-model="scope.row.appointment"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  active-value="2"
                  inactive-value="1"
                  active-text="Y"
                  inactive-text="N">
                </el-switch>
              </template>
            </el-table-column> -->
            <el-table-column
              align="center"
              prop="communicate"
              width="200px"
              title="communicate"
              label="沟通内容">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.communicate">
                  <div slot>{{scope.row.communicate}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="interviewDate"
              width="150px"
              label="面试日期">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.interviewDate">
                  <div slot>{{scope.row.interviewDate}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="recommend"
              label="推荐客户">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.recommend">
                  <div slot>{{scope.row.recommend}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              width="85"
              prop="insideResult"
              label="内面结果">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.insideResult">
                  <div slot>{{scope.row.insideResult}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              width="85"
              prop="customerResult"
              label="客面结果">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.customerResult">
                  <div slot>{{scope.row.customerResult}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              width="95"
              prop="hrName"
              label="人事">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.hrName">
                  <div slot>{{scope.row.hrName}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              width="95"
              prop="remark"
              label="备注">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.remark">
                  <div slot>{{scope.row.remark}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              width="70"
              prop="channel"
              label="渠道">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.channel">
                  <div slot>{{scope.row.channel}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="tags"
              label="标签">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.tags">
                  <div slot>{{scope.row.tags}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <!--
            <el-table-column
              align="center"
              prop="status"
              label="状态">
            </el-table-column>-->
            <el-table-column
              align="center"
              prop="addDate"
              width="150px"
              label="添加时间">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.addDate">
                  <div slot>{{scope.row.addDate}}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditTalentView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button style="padding: 3px 4px 3px 4px;margin: 2px" type="primary"
                           @click="showStatusDialog(scope.row)"
                           size="mini">状态流转
                </el-button>
                <!--
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteEmp(scope.row)">删除
                </el-button>-->
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <!--<el-button type="danger" size="mini" v-if="talents.length>0" :disabled="multipleSelection.length==0"
                     @click="deleteManyEmps">批量删除
          </el-button>-->
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
              <el-form-item label="电话:" prop="phone">
                <el-input prefix-icon="el-icon-edit" v-model="talent.phone" size="mini" style="width: 200px"
                          placeholder="请输入电话"></el-input>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <el-form-item label="技术:" prop="technology">
                <el-input prefix-icon="el-icon-edit" v-model="talent.technology" size="mini" style="width: 200px"
                          placeholder="请输入技术"></el-input>
              </el-form-item>
            </div>
          </el-col>

          <el-col :span="8">
            <div>
              <el-form-item label="现工作地点:" prop="workCity">
                <el-input prefix-icon="el-icon-edit" v-model="talent.workCity" size="mini" style="width: 200px"
                          placeholder="请输入现工作地点"></el-input>
              </el-form-item>
            </div>
          </el-col>

          <el-col :span="8">
            <div>
              <el-form-item label="最近在职公司:" prop="lastCompany">
                <el-input prefix-icon="el-icon-edit" v-model="talent.lastCompany" size="mini" style="width: 200px"
                          placeholder="请输入最近在职公司"></el-input>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <el-form-item label="毕业学校:" prop="school">
                <el-input prefix-icon="el-icon-edit" v-model="talent.school" size="mini" style="width: 200px"
                          placeholder="请输入毕业学校"></el-input>
              </el-form-item>
            </div>
          </el-col>


          <el-col :span="8">
            <div>

              <el-form-item label="毕业时间:" prop="major">
                <el-input prefix-icon="el-icon-edit" v-model="talent.graduation" size="mini" style="width: 200px"
                          placeholder="请输入毕业时间"></el-input>
              </el-form-item>
            </div>
          </el-col>


          <el-col :span="8">
            <div>
              <el-form-item label="专业:" prop="major">
                <el-input prefix-icon="el-icon-edit" v-model="talent.major" size="mini" style="width: 200px"
                          placeholder="请输入专业"></el-input>
              </el-form-item>
            </div>
          </el-col>

          <el-col :span="8">
            <div>
              <el-form-item label="学历:" prop="education">
                <el-input prefix-icon="el-icon-edit" v-model="talent.education" size="mini" style="width: 200px"
                          placeholder="请输入学历"></el-input>
              </el-form-item>
            </div>
          </el-col>

          <el-col :span="8">
            <div>
              <el-form-item label="先薪资:" prop="lastSalary">
                <el-input prefix-icon="el-icon-edit" v-model="talent.lastSalary" size="mini" style="width: 200px"
                          placeholder="请输入先薪资"></el-input>
              </el-form-item>
            </div>
          </el-col>

          <el-col :span="8">
            <div>
              <el-form-item label="期望薪资:" prop="hopeSalary">
                <el-input prefix-icon="el-icon-edit" v-model="talent.hopeSalary" size="mini" style="width: 200px"
                          placeholder="请输入期望薪资"></el-input>
              </el-form-item>
            </div>
          </el-col>
          <!--
          <el-col :span="6">
            <div>
              <el-form-item label="是否预约成功:" prop="appointment" style=" height: 41px;">
                <el-radio-group v-model="talent.appointment" style="width: 150px;   ">
                  <el-radio label="1">N</el-radio>
                  <el-radio style="margin-left: 15px" label="2">Y</el-radio>
                </el-radio-group>
              </el-form-item>
            </div>
          </el-col> -->
            <el-col :span="8">
              <div>
                <el-form-item label="沟通内容:" prop="communicate">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.communicate" size="mini" style="width: 200px"
                            placeholder="请输入沟通内容"></el-input>
                </el-form-item>
              </div>
            </el-col>


            <el-col :span="8">
              <div>
                <el-form-item label="面试日期:" prop="interviewDate">
                  <el-date-picker
                    v-model="talent.interviewDate"
                    size="mini"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    style="width: 200px"
                    type="datetime"
                    placeholder="面试日期">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <el-form-item label="推荐客户:" prop="recommend">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.recommend" size="mini" style="width: 200px"
                            placeholder="请输入推荐客户"></el-input>
                </el-form-item>
              </div>
            </el-col>

            <el-col :span="8">
              <div>
                <el-form-item label="内面结果:" prop="insideResult">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.insideResult" size="mini" style="width: 200px"
                            placeholder="请输入内面结果"></el-input>
                </el-form-item>
              </div>
            </el-col>

            <el-col :span="8">
              <div>
                <el-form-item label="客面结果:" prop="customerResult">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.customerResult" size="mini" style="width: 200px"
                            placeholder="请输入客面结果"></el-input>
                </el-form-item>
              </div>
            </el-col>

            <el-col :span="8">
              <div>
                <el-form-item label="备注:" prop="remark">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.remark" size="mini" style="width: 200px"
                            placeholder="请输入备注"></el-input>
                </el-form-item>
              </div>
            </el-col>

            <el-col :span="8">
              <div>
                <el-form-item label="渠道:" prop="channel">
                  <el-input prefix-icon="el-icon-edit" v-model="talent.channel" size="mini" style="width: 200px"
                            placeholder="请输入渠道"></el-input>
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
    <el-dialog
      :title="statusDialogTitle"
      :before-close="dialogClose"
      style="padding: 0px;"
      :close-on-click-modal="false"
      :visible.sync="statusDialogVisible"
      width="77%">

      <template>
        <div v-if="talent.status == 0 || talent.status == 2">
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="预约" v-if="talent.status == 0" @change="updateStatus">
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
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="是否应约内面" v-if="talent.status == 1" @change="updateStatus">
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
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="内面结果" v-if="talent.status == 3" @change="updateStatus">
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
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="是否应约客面" v-if="talent.status == 5" @change="updateStatus">
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
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="客面结果" v-if="talent.status == 7" @change="updateStatus">
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
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="offer结果" v-if="talent.status == 9" @change="updateStatus">
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
          <el-select v-model="talent.status" style="width: 130px" size="mini" placeholder="入职结果" v-if="talent.status == 11" @change="updateStatus">
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
        beginDateScope: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        statusDialogTitle: '',
        depTextColor: '#c0c4cc',
        multipleSelection: [],
        hrList: [],
        totalCount: -1,
        currentPage: 1,
        pageSize : 10,
        oldStatus:null,
        level:null,  //定级
        profits:null, //利润
        entryDate:null , //到岗时间
        entryMark:null, //备注
        //appointmentList: [{id: 1, name: '否'}, {id: 2, name: '是'}],
        status: {
          appoint: [{id: 0, name: '预约结果'}, {id: 1, name: '预约成功'}, {id: 2, name: '预约失败'}],
          isInside:[{id: 1, name: '是否应约内面'}, {id: 3, name: '参加内面'}, {id: 4, name: '未参加内面'}],
          inside: [{id: 3, name: '内面结果'}, {id: 5, name: '内面成功'}, {id: 6, name: '内面失败'}],
          isCustomer:[{id: 5, name: '是否应约客面'}, {id: 7, name: '参加客面'}, {id: 8, name: '未参加客面'}],
          customer: [{id: 7, name: '客面结果'}, {id: 9, name: '客面成功'}, {id: 10, name: '客面失败'}],
          offer: [{id: 9, name: 'offer结果'}, {id: 11, name: 'offer成功'}, {id: 12, name: 'offer失败'}],
          entry: [{id: 11, name: '入职结果'}, {id: 13, name: '入职成功'}, {id: 14, name: '入职失败'}],
          finish: [{id: 13, name: '是否完成'}, {id: 15, name: '已完成'}, {id: 16, name: '未完成'}]
        },
        allStatus:[{id: 1, name: '预约成功'}, {id: 2, name: '预约失败'},{id: 3, name: '参加内面'}, {id: 4, name: '未参加内面'},{id: 5, name: '内面成功'}, {id: 6, name: '内面失败'},
          {id: 7, name: '参加客面'}, {id: 8, name: '未参加客面'}, {id: 9, name: '客面成功'}, {id: 10, name: '客面失败'}, {id: 11, name: 'offer成功'}, {id: 12, name: 'offer失败'} ,
          {id: 13, name: '入职成功'}, {id: 14, name: '入职失败'}, {id: 15, name: '已完成'}, {id: 16, name: '未完成'}],
        dialogVisible: false,
        tableLoading: false,
        advanceSearchViewVisible: false,
        showOrHidePop: false,
        showOrHidePop2: false,
        statusDialogVisible: false,
        talent: {
          id: null,
          hrId: null,
          name: null,
          phone: null,
          technology: null,
          workCity: null,
          lastCompany: null,
          school: null,
          graduation: null,
          major: null,
          education: null,
          lastSalary: null,
          hopeSalary: null,
          communicate: null,
          interviewDate: null,
          recommend: null,
          insideResult: null,
          customerResult: null,
          hrName: null,
          remark: null,
          channel: null,
          status: null,
          level : null,
          profits : null,
          entryDate : null,
          entryMark : null
        },
        rules: {
          name: [{required: true, message: '必填:姓名', trigger: 'blur'}],
          phone: [{required: true, message: '必填:电话', trigger: 'blur'}]
        }, pickerOptions: {
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
        countStart:null,
        countEnd:null
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
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.talents = data.data.talents;
            _this.totalCount = data.data.count;
          }
        })
      }, currentChange(currentChange) {
        this.currentPage = currentChange;
        this.loadTalent();
      }, searchTalent() {
        this.loadTalent();
      }, nameChange() {
        this.loadTalent();
      }, updateStatus() {
        this.postRequest("/talent/basic/updateTalent", {"id": this.talent.id, "status": this.talent.status}).then(resp => {
          if (resp && resp.status == 200) {
            var data = resp.data;
            if (data.resultCode == 1) {
              this.$message({type: resp.status, message: "流转成功!"});
            }else  if (data.resultCode == 10001) {
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
      emptyTalentData() {
        this.talent = {
          id: null,
          hrId: null,
          name: null,
          phone: null,
          technology: null,
          workCity: null,
          lastCompany: null,
          school: null,
          graduation: null,
          major: null,
          education: null,
          lastSalary: null,
          hopeSalary: null,
          communicate: null,
          interviewDate: null,
          recommend: null,
          insideResult: null,
          customerResult: null,
          hrName: null,
          remark: null,
          channel: null,
          status: null,
          level : null,
          profits : null,
          entryDate : null,
          entryMark : null

        }
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

            for(var o in this.talent){
              if(this.talent[o]){
                obj[o] = this.talent[o];
              }
            }
            if (this.talent.id) {
              //更新
              this.postRequest("/talent/basic/updateTalent", obj).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  if (data.resultCode == 1) {
                    this.$message({type: resp.status, message: "编辑成功!"});
                  }else  if (data.resultCode == 10001) {
                    this.$message({type: resp.status, message: "一周内不能编辑!"});
                  }
                  _this.dialogVisible = false;
                  //_this.emptyTalentData();
                  //_this.loadTalent();
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
                  }
                  _this.dialogVisible = false;
                  //_this.emptyTalentData();
                  //_this.loadTalent();
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
      },exportCount() {
        if(this.countStart && this.countEnd){
          var url = "/talent/basic/exportCount?countStart=" + new Date(this.countStart).valueOf() + "&countEnd=" + new Date(this.countEnd).valueOf();
          window.open(url, "_parent");
        }else{
          this.$message({message: "请选择导出时间!"});
        }
      },updateEntryInfo(){
        var _this = this;
        var obj = new Object();
        obj['id'] = this.talent.id;
        if(this.talent.level){
          obj['level'] = this.talent.level;
        }
        if(this.talent.profits){
          obj['profits'] = this.talent.profits;
        }
        if(this.talent.entryDate){
          obj['entryDate'] = this.talent.entryDate;
        }
        if(this.talent.entryMark){
          obj['entryMark'] = this.talent.entryMark;
        }

        this.postRequest("/talent/basic/updateTalent", obj).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            if (data.resultCode == 1) {
              this.$message({type: resp.status, message: "编辑成功!"});
            }else  if (data.resultCode == 10001) {
              this.$message({type: resp.status, message: "一周内不能编辑!"});
            }
          }
        })
      },dialogClose(done){
        this.emptyTalentData();
        done();
      }
    }
  }
</script>

<style>
  .el-table .cell {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    white-space: nowrap!important;
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

  .is-leaf{
    font-family: STHeiti Light;
    background-color: #abdae4!important;
  }
  .el-table td, .el-table th.is-leaf {
    border-bottom: 1px solid gray;
  }
  .el-table--border td, .el-table--border th, .el-table__body-wrapper .el-table--border.is-scrolling-left~.el-table__fixed {
    border-right: 1px solid gray;
  }
</style>
