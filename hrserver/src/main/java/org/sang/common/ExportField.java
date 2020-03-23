package org.sang.common;
/**
 * Forward
 *
 * @author clq
 * @date 2018/04/25
 */
public interface ExportField {

	 interface ExportTitle {
		 String[] TALENT_TITLE = { "序号", "添加日期", "姓名", "技术","现工作地点","最近在职公司","电话","毕业学校","毕业时间","专业","学历","先薪资","期望薪资","状态","沟通内容","面试日期","推荐客户","内面结果","客面结果","HR","备注","渠道","定级","利润","到岗时间","到岗备注" };
		 String[] TALENT_COUNT_TITLE = {  "序号","名称","hr","推荐客户","推荐时间","岗位","工作年限","工作进展" };
		 String[] TALENT_OFFER_TITLE = {  "HR", "姓名", "Domain","工作地点","定级","利润","到岗时间","备注" };
	}

	 interface ExportColumn {
		 String[] TALENT_COLUMN = { "Id", "AddDate", "Name", "Technology","WorkCity","LastCompany","Phone","School","Graduation","Major","Education","LastSalary","HopeSalary","Appointment","Communicate","InterviewDate","Recommend","InsideResult","CustomerResult","HrName","Remark","Channel","Level","Profits","EntryDate","EntryMark"};
		 String[] TALENT_COUNT_COLUMN = {  "id", "name", "userName","recommendClient","recommendTime","job","workAge","progress"};

	}
}
