package com.sot.iexam.service.front;

import com.sot.iexam.DO.reviewerTitle;

import java.util.Map;

public interface ReviewerTitleService {

    boolean addRegistrationTitle(reviewerTitle reviewerTitle, Map result);

    void removeReviewer(reviewerTitle reviewerTitle);
}
