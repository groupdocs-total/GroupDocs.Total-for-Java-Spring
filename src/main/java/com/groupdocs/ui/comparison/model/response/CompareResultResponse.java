package com.groupdocs.ui.comparison.model.response;

import com.groupdocs.comparison.common.changes.ChangeInfo;

public class CompareResultResponse {
    private ChangeInfo[] changes;

    public void setChanges(ChangeInfo[] changes) {
        this.changes = changes;
    }

    public ChangeInfo[] getChanges() {
        return changes;
    }
}
