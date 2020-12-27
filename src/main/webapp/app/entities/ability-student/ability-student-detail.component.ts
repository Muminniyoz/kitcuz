import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAbilityStudent } from 'app/shared/model/ability-student.model';

@Component({
  selector: 'jhi-ability-student-detail',
  templateUrl: './ability-student-detail.component.html',
})
export class AbilityStudentDetailComponent implements OnInit {
  abilityStudent: IAbilityStudent | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ abilityStudent }) => (this.abilityStudent = abilityStudent));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
