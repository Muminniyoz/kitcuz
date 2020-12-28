import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICourseRequests, CourseRequests } from 'app/shared/model/course-requests.model';
import { CourseRequestsService } from './course-requests.service';
import { ICourses } from 'app/shared/model/courses.model';
import { CoursesService } from 'app/entities/courses/courses.service';
import { ICourseGroup } from 'app/shared/model/course-group.model';
import { CourseGroupService } from 'app/entities/course-group/course-group.service';

type SelectableEntity = ICourses | ICourseGroup;

@Component({
  selector: 'jhi-course-requests-update',
  templateUrl: './course-requests-update.component.html',
})
export class CourseRequestsUpdateComponent implements OnInit {
  isSaving = false;
  courses: ICourses[] = [];
  coursegroups: ICourseGroup[] = [];
  dateOfBirthDp: any;
  registerationDateDp: any;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    middleName: [],
    email: [],
    dateOfBirth: [],
    gender: [],
    registerationDate: [],
    telephone: [],
    mobile: [],
    coursesId: [],
    coursesGroupId: [],
  });

  constructor(
    protected courseRequestsService: CourseRequestsService,
    protected coursesService: CoursesService,
    protected courseGroupService: CourseGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courseRequests }) => {
      this.updateForm(courseRequests);

      this.coursesService.query().subscribe((res: HttpResponse<ICourses[]>) => (this.courses = res.body || []));

      this.courseGroupService.query().subscribe((res: HttpResponse<ICourseGroup[]>) => (this.coursegroups = res.body || []));
    });
  }

  updateForm(courseRequests: ICourseRequests): void {
    this.editForm.patchValue({
      id: courseRequests.id,
      firstName: courseRequests.firstName,
      lastName: courseRequests.lastName,
      middleName: courseRequests.middleName,
      email: courseRequests.email,
      dateOfBirth: courseRequests.dateOfBirth,
      gender: courseRequests.gender,
      registerationDate: courseRequests.registerationDate,
      telephone: courseRequests.telephone,
      mobile: courseRequests.mobile,
      coursesId: courseRequests.coursesId,
      coursesGroupId: courseRequests.coursesGroupId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const courseRequests = this.createFromForm();
    if (courseRequests.id !== undefined) {
      this.subscribeToSaveResponse(this.courseRequestsService.update(courseRequests));
    } else {
      this.subscribeToSaveResponse(this.courseRequestsService.create(courseRequests));
    }
  }

  private createFromForm(): ICourseRequests {
    return {
      ...new CourseRequests(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      registerationDate: this.editForm.get(['registerationDate'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      coursesId: this.editForm.get(['coursesId'])!.value,
      coursesGroupId: this.editForm.get(['coursesGroupId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourseRequests>>): void {
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
