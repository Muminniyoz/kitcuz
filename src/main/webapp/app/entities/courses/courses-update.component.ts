import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICourses, Courses } from 'app/shared/model/courses.model';
import { CoursesService } from './courses.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill/skill.service';

@Component({
  selector: 'jhi-courses-update',
  templateUrl: './courses-update.component.html',
})
export class CoursesUpdateComponent implements OnInit {
  isSaving = false;
  skills: ISkill[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    about: [],
    price: [null, [Validators.min(0)]],
    imageUrl: [],
    skills: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected coursesService: CoursesService,
    protected skillService: SkillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courses }) => {
      this.updateForm(courses);

      this.skillService.query().subscribe((res: HttpResponse<ISkill[]>) => (this.skills = res.body || []));
    });
  }

  updateForm(courses: ICourses): void {
    this.editForm.patchValue({
      id: courses.id,
      title: courses.title,
      about: courses.about,
      price: courses.price,
      imageUrl: courses.imageUrl,
      skills: courses.skills,
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
    const courses = this.createFromForm();
    if (courses.id !== undefined) {
      this.subscribeToSaveResponse(this.coursesService.update(courses));
    } else {
      this.subscribeToSaveResponse(this.coursesService.create(courses));
    }
  }

  private createFromForm(): ICourses {
    return {
      ...new Courses(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      about: this.editForm.get(['about'])!.value,
      price: this.editForm.get(['price'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      skills: this.editForm.get(['skills'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourses>>): void {
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
