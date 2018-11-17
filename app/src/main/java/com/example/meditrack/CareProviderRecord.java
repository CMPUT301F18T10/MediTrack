package com.example.meditrack;

import com.example.meditrack.AbstractRecord;

import java.util.Date;
import java.util.Objects;

public class CareProviderRecord extends AbstractRecord implements ElasticsearchStorable{

    private String careProviderComment;
    private String careProviderID;

    public CareProviderRecord(String problemId, String careProviderComment, String careProviderID) {
        super(problemId);
        this.careProviderComment = careProviderComment;
        this.careProviderID = careProviderID;
    }

    public String getCareProviderComment() {
        return careProviderComment;
    }

    public String getCareProviderID() {
        return careProviderID;
    }

    @Override
    public String getElasticsearchType(){
        return "care_provider_records";
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CareProviderRecord that = (CareProviderRecord) o;
        return Objects.equals(getCareProviderComment(), that.getCareProviderComment()) &&
                Objects.equals(getCareProviderID(), that.getCareProviderID());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCareProviderComment(), getCareProviderID());
    }
}
