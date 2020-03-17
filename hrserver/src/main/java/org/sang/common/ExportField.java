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
		 String[] TALENT_COUNT_TITLE = {  "HR", "通话量", "邀约成功量","邀约成功比例","参加内面量","内面成功量","内面成功比例","参加复试量","复试通过量","复试通过比例","offer量","offer比例","入职量" };
		 String[] TALENT_OFFER_TITLE = {  "HR", "姓名", "Domain","工作地点","定级","利润","到岗时间","备注" };
	}

	 interface ExportColumn {
		 String[] TALENT_COLUMN = { "Id", "AddDate", "Name", "Technology","WorkCity","LastCompany","Phone","School","Graduation","Major","Education","LastSalary","HopeSalary","Appointment","Communicate","InterviewDate","Recommend","InsideResult","CustomerResult","HrName","Remark","Channel","Level","Profits","EntryDate","EntryMark"};
		 String[] TALENT_COUNT_COLUMN = {  "HrName", "PhoneCount", "PhoneSuccessCount","PhoneSuccessPercent","IsInsideSuccessCount","InsideSuccessCount","InsideSuccessPercent","IsCustomerSuccessCount","CustomerSuccessCount","CustomerSuccessPercent","OfferSuccessCount","OfferSuccessPercent","EntrySuccessCount" };
		 String[] TALENT_OFFER_COLUMN  = {  "HrName", "Name", "Technology","Recommend","Level","Profits","EntryDate","EntryMark"};

	}
}
