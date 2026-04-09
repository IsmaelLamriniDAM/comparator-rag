package comparator.ia.app.dtos.chat;

import comparator.ia.app.dtos.logic.DataDto;

public class AnswerDTO {
    DataDto data;
    String questionUser;

    public DataDto getData() {
        return data;
    }

    public void setData(DataDto data) {
        this.data = data;
    }

    public String getQuestionUser() {
        return questionUser;
    }

    public void setQuestionUser(String questionUser) {
        this.questionUser = questionUser;
    }
}
