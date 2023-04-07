
package client.legalease.Model.QuizModel.QuestionSet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quizset {


        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("iscomplete")
        @Expose
        private String iscomplete;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIscomplete() {
            return iscomplete;
        }

        public void setIscomplete(String iscomplete) {
            this.iscomplete = iscomplete;
        }

    }