package com.example.mygraduation_s.javabean;

public class QuestionBean {

	private int _id;
	private int q_type;// ���ͣ�0���ж��� 1��ѡ����
	private String title;// ����
	private String optionA;// ѡ��A
	private String optionB;// ѡ��B
	private String optionC;// ѡ��C
	private String optionD;// ѡ��D
	private int answer;// ��
	private byte[] picture;// ��Щ�����к�ͼƬ

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getQ_type() {
		return q_type;
	}

	public void setQ_type(int q_type) {
		this.q_type = q_type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public QuestionBean(int _id, String title, String optionA, String optionB,
			String optionC, String optionD, int answer, int q_type,
			byte[] picture) {
		super();
		this._id = _id;
		this.q_type = q_type;
		this.title = title;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.answer = answer;
		this.picture = picture;
	}

}
