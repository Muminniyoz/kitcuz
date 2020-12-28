import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICourseGroup, CourseGroup } from 'app/shared/model/course-group.model';
import { CourseGroupService } from './course-group.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher/teacher.service';
import { IPlanning } from 'app/shared/model/planning.model';
import { PlanningService } from 'app/entities/planning/planning.service';

type SelectableEntity = ITeacher | IPlanning;

@Component({
  selector: 'jhi-course-group-update',
  templateUrl: './course-group-update.component.html',
})
export class CourseGroupUpdateComponent implements OnInit {
  isSaving = false;
  teachers: ITeacher[] = [];
  plannings: IPlanning[] = [];
  startDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    startDate: [],
    status: [],
    teacherId: [],
    planningId: [],
  });

  constructor(
    protected courseGroupService: CourseGroupService,
    protected teacherService: TeacherService,
    protected planningService: PlanningService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courseGroup }) => {
      this.updateForm(courseGroup);

      this.teacherService.query().subscribe((res: HttpResponse<ITeacher[]>) => (this.teachers = res.body || []));

      this.planningService.query().subscribe((res: HttpResponse<IPlanning[]>) => (this.plannings = res.body || []));
    });
  }

  updateForm(courseGroup: ICourseGroup): void {
    this.editForm.patchValue({
      id: courseGroup.id,
      name: courseGroup.name,
      startDate: courseGroup.startDate,
      status: courseGroup.status,
      teacherId: courseGroup.teacherId,
      planningId: courseGroup.planningId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const courseGroup = this.createFromForm();
    if (courseGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.courseGroupService.update(courseGroup));
    } else {
      this.subscribeToSaveResponse(this.courseGroupService.create(courseGroup));
    }
  }

  private createFromForm(): ICourseGroup {
    return {
      ...new CourseGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      teacherId: this.editForm.get(['teacherId'])!.value,
      planningId: this.editForm.get(['planningId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourseGroup>>): void {
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
