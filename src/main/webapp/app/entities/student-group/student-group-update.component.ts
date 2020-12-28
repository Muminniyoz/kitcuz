import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStudentGroup, StudentGroup } from 'app/shared/model/student-group.model';
import { StudentGroupService } from './student-group.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';
import { ICourseGroup } from 'app/shared/model/course-group.model';
import { CourseGroupService } from 'app/entities/course-group/course-group.service';

type SelectableEntity = IStudent | ICourseGroup;

@Component({
  selector: 'jhi-student-group-update',
  templateUrl: './student-group-update.component.html',
})
export class StudentGroupUpdateComponent implements OnInit {
  isSaving = false;
  students: IStudent[] = [];
  coursegroups: ICourseGroup[] = [];
  startingDateDp: any;

  editForm = this.fb.group({
    id: [],
    startingDate: [],
    active: [],
    contractNumber: [],
    studentId: [],
    groupId: [],
  });

  constructor(
    protected studentGroupService: StudentGroupService,
    protected studentService: StudentService,
    protected courseGroupService: CourseGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studentGroup }) => {
      this.updateForm(studentGroup);

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));

      this.courseGroupService.query().subscribe((res: HttpResponse<ICourseGroup[]>) => (this.coursegroups = res.body || []));
    });
  }

  updateForm(studentGroup: IStudentGroup): void {
    this.editForm.patchValue({
      id: studentGroup.id,
      startingDate: studentGroup.startingDate,
      active: studentGroup.active,
      contractNumber: studentGroup.contractNumber,
      studentId: studentGroup.studentId,
      groupId: studentGroup.groupId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const studentGroup = this.createFromForm();
    if (studentGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.studentGroupService.update(studentGroup));
    } else {
      this.subscribeToSaveResponse(this.studentGroupService.create(studentGroup));
    }
  }

  private createFromForm(): IStudentGroup {
    return {
      ...new StudentGroup(),
      id: this.editForm.get(['id'])!.value,
      startingDate: this.editForm.get(['startingDate'])!.value,
      active: this.editForm.get(['active'])!.value,
      contractNumber: this.editForm.get(['contractNumber'])!.value,
      studentId: this.editForm.get(['studentId'])!.value,
      groupId: this.editForm.get(['groupId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentGroup>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
