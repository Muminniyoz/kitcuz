import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourseRequests } from 'app/shared/model/course-requests.model';

@Component({
  selector: 'jhi-course-requests-detail',
  templateUrl: './course-requests-detail.component.html',
})
export class CourseRequestsDetailComponent implements OnInit {
  courseRequests: ICourseRequests | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courseRequests }) => (this.courseRequests = courseRequests));
  }

  previousState(): void {
    window.history.back();
  }
}
