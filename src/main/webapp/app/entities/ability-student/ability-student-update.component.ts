import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAbilityStudent, AbilityStudent } from 'app/shared/model/ability-student.model';
import { AbilityStudentService } from './ability-student.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-ability-student-update',
  templateUrl: './ability-student-update.component.html',
})
export class AbilityStudentUpdateComponent implements OnInit {
  isSaving = false;
  dateOfBirthDp: any;
  registerationDateDp: any;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    middleName: [],
    about: [],
    email: [],
    dateOfBirth: [],
    registerationDate: [],
    telephone: [],
    mobile: [],
    thumbnailPhotoUrl: [],
    fullPhotoUrl: [],
    isShowing: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected abilityStudentService: AbilityStudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ abilityStudent }) => {
      this.updateForm(abilityStudent);
    });
  }

  updateForm(abilityStudent: IAbilityStudent): void {
    this.editForm.patchValue({
      id: abilityStudent.id,
      firstName: abilityStudent.firstName,
      lastName: abilityStudent.lastName,
      middleName: abilityStudent.middleName,
      about: abilityStudent.about,
      email: abilityStudent.email,
      dateOfBirth: abilityStudent.dateOfBirth,
      registerationDate: abilityStudent.registerationDate,
      telephone: abilityStudent.telephone,
      mobile: abilityStudent.mobile,
      thumbnailPhotoUrl: abilityStudent.thumbnailPhotoUrl,
      fullPhotoUrl: abilityStudent.fullPhotoUrl,
      isShowing: abilityStudent.isShowing,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('kitcuzApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const abilityStudent = this.createFromForm();
    if (abilityStudent.id !== undefined) {
      this.subscribeToSaveResponse(this.abilityStudentService.update(abilityStudent));
    } else {
      this.subscribeToSaveResponse(this.abilityStudentService.create(abilityStudent));
    }
  }

  private createFromForm(): IAbilityStudent {
    return {
      ...new AbilityStudent(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      about: this.editForm.get(['about'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      registerationDate: this.editForm.get(['registerationDate'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      thumbnailPhotoUrl: this.editForm.get(['thumbnailPhotoUrl'])!.value,
      fullPhotoUrl: this.editForm.get(['fullPhotoUrl'])!.value,
      isShowing: this.editForm.get(['isShowing'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbilityStudent>>): void {
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
