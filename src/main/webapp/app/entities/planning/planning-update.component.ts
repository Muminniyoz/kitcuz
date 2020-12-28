import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlanning, Planning } from 'app/shared/model/planning.model';
import { PlanningService } from './planning.service';
import { ICourses } from 'app/shared/model/courses.model';
import { CoursesService } from 'app/entities/courses/courses.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher/teacher.service';

type SelectableEntity = ICourses | ITeacher;

@Component({
  selector: 'jhi-planning-update',
  templateUrl: './planning-update.component.html',
})
export class PlanningUpdateComponent implements OnInit {
  isSaving = false;
  courses: ICourses[] = [];
  teachers: ITeacher[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    about: [],
    duration: [],
    fileUrl: [],
    courseId: [],
    teacherId: [],
  });

  constructor(
    protected planningService: PlanningService,
    protected coursesService: CoursesService,
    protected teacherService: TeacherService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planning }) => {
      this.updateForm(planning);

      this.coursesService.query().subscribe((res: HttpResponse<ICourses[]>) => (this.courses = res.body || []));

      this.teacherService.query().subscribe((res: HttpResponse<ITeacher[]>) => (this.teachers = res.body || []));
    });
  }

  updateForm(planning: IPlanning): void {
    this.editForm.patchValue({
      id: planning.id,
      name: planning.name,
      about: planning.about,
      duration: planning.duration,
      fileUrl: planning.fileUrl,
      courseId: planning.courseId,
      teacherId: planning.teacherId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planning = this.createFromForm();
    if (planning.id !== undefined) {
      this.subscribeToSaveResponse(this.planningService.update(planning));
    } else {
      this.subscribeToSaveResponse(this.planningService.create(planning));
    }
  }

  private createFromForm(): IPlanning {
    return {
      ...new Planning(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      about: this.editForm.get(['about'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      fileUrl: this.editForm.get(['fileUrl'])!.value,
      courseId: this.editForm.get(['courseId'])!.value,
      teacherId: this.editForm.get(['teacherId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanning>>): void {
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
