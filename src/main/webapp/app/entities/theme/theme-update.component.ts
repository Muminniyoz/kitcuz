import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITheme, Theme } from 'app/shared/model/theme.model';
import { ThemeService } from './theme.service';
import { IPlanning } from 'app/shared/model/planning.model';
import { PlanningService } from 'app/entities/planning/planning.service';

@Component({
  selector: 'jhi-theme-update',
  templateUrl: './theme-update.component.html',
})
export class ThemeUpdateComponent implements OnInit {
  isSaving = false;
  plannings: IPlanning[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    number: [],
    isSection: [],
    about: [],
    homeWorkAbouts: [],
    fileUrl: [],
    planningId: [],
  });

  constructor(
    protected themeService: ThemeService,
    protected planningService: PlanningService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ theme }) => {
      this.updateForm(theme);

      this.planningService.query().subscribe((res: HttpResponse<IPlanning[]>) => (this.plannings = res.body || []));
    });
  }

  updateForm(theme: ITheme): void {
    this.editForm.patchValue({
      id: theme.id,
      name: theme.name,
      number: theme.number,
      isSection: theme.isSection,
      about: theme.about,
      homeWorkAbouts: theme.homeWorkAbouts,
      fileUrl: theme.fileUrl,
      planningId: theme.planningId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const theme = this.createFromForm();
    if (theme.id !== undefined) {
      this.subscribeToSaveResponse(this.themeService.update(theme));
    } else {
      this.subscribeToSaveResponse(this.themeService.create(theme));
    }
  }

  private createFromForm(): ITheme {
    return {
      ...new Theme(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      number: this.editForm.get(['number'])!.value,
      isSection: this.editForm.get(['isSection'])!.value,
      about: this.editForm.get(['about'])!.value,
      homeWorkAbouts: this.editForm.get(['homeWorkAbouts'])!.value,
      fileUrl: this.editForm.get(['fileUrl'])!.value,
      planningId: this.editForm.get(['planningId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITheme>>): void {
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

  trackById(index: number, item: IPlanning): any {
    return item.id;
  }
}
