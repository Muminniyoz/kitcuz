import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentGroup } from 'app/shared/model/student-group.model';

@Component({
  selector: 'jhi-student-group-detail',
  templateUrl: './student-group-detail.component.html',
})
export class StudentGroupDetailComponent implements OnInit {
  studentGroup: IStudentGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studentGroup }) => (this.studentGroup = studentGroup));
  }

  previousState(): void {
    window.history.back();
  }
}
