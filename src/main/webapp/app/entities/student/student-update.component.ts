import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html',
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;
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
    lastAccess: [],
    telephone: [],
    mobile: [],
    thumbnailPhotoUrl: [],
    fullPhotoUrl: [],
    active: [],
    key: [],
  });

  constructor(protected studentService: StudentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      if (!student.id) {
        const today = moment().startOf('day');
        student.lastAccess = today;
      }

      this.updateForm(student);
    });
  }

  updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      firstName: student.firstName,
      lastName: student.lastName,
      middleName: student.middleName,
      email: student.email,
      dateOfBirth: student.dateOfBirth,
      gender: student.gender,
      registerationDate: student.registerationDate,
      lastAccess: student.lastAccess ? student.lastAccess.format(DATE_TIME_FORMAT) : null,
      telephone: student.telephone,
      mobile: student.mobile,
      thumbnailPhotoUrl: student.thumbnailPhotoUrl,
      fullPhotoUrl: student.fullPhotoUrl,
      active: student.active,
      key: student.key,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      registerationDate: this.editForm.get(['registerationDate'])!.value,
      lastAccess: this.editForm.get(['lastAccess'])!.value ? moment(this.editForm.get(['lastAccess'])!.value, DATE_TIME_FORMAT) : undefined,
      telephone: this.editForm.get(['telephone'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      thumbnailPhotoUrl: this.editForm.get(['thumbnailPhotoUrl'])!.value,
      fullPhotoUrl: this.editForm.get(['fullPhotoUrl'])!.value,
      active: this.editForm.get(['active'])!.value,
      key: this.editForm.get(['key'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
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
}
