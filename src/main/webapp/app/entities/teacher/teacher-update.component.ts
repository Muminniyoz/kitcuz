import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITeacher, Teacher } from 'app/shared/model/teacher.model';
import { TeacherService } from './teacher.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill/skill.service';

@Component({
  selector: 'jhi-teacher-update',
  templateUrl: './teacher-update.component.html',
})
export class TeacherUpdateComponent implements OnInit {
  isSaving = false;
  skills: ISkill[] = [];
  dateOfBirthDp: any;
  registerationDateDp: any;
  leaveDateDp: any;

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
    about: [],
    portfolia: [],
    leaveDate: [],
    isShowingHome: [],
    imageUrl: [],
    skills: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected teacherService: TeacherService,
    protected skillService: SkillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teacher }) => {
      if (!teacher.id) {
        const today = moment().startOf('day');
        teacher.lastAccess = today;
      }

      this.updateForm(teacher);

      this.skillService.query().subscribe((res: HttpResponse<ISkill[]>) => (this.skills = res.body || []));
    });
  }

  updateForm(teacher: ITeacher): void {
    this.editForm.patchValue({
      id: teacher.id,
      firstName: teacher.firstName,
      lastName: teacher.lastName,
      middleName: teacher.middleName,
      email: teacher.email,
      dateOfBirth: teacher.dateOfBirth,
      gender: teacher.gender,
      registerationDate: teacher.registerationDate,
      lastAccess: teacher.lastAccess ? teacher.lastAccess.format(DATE_TIME_FORMAT) : null,
      telephone: teacher.telephone,
      mobile: teacher.mobile,
      thumbnailPhotoUrl: teacher.thumbnailPhotoUrl,
      fullPhotoUrl: teacher.fullPhotoUrl,
      active: teacher.active,
      key: teacher.key,
      about: teacher.about,
      portfolia: teacher.portfolia,
      leaveDate: teacher.leaveDate,
      isShowingHome: teacher.isShowingHome,
      imageUrl: teacher.imageUrl,
      skills: teacher.skills,
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
    const teacher = this.createFromForm();
    if (teacher.id !== undefined) {
      this.subscribeToSaveResponse(this.teacherService.update(teacher));
    } else {
      this.subscribeToSaveResponse(this.teacherService.create(teacher));
    }
  }

  private createFromForm(): ITeacher {
    return {
      ...new Teacher(),
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
      about: this.editForm.get(['about'])!.value,
      portfolia: this.editForm.get(['portfolia'])!.value,
      leaveDate: this.editForm.get(['leaveDate'])!.value,
      isShowingHome: this.editForm.get(['isShowingHome'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      skills: this.editForm.get(['skills'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeacher>>): void {
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

  trackById(index: number, item: ISkill): any {
    return item.id;
  }

  getSelected(selectedVals: ISkill[], option: ISkill): ISkill {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
