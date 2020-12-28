import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGalereya } from 'app/shared/model/galereya.model';

@Component({
  selector: 'jhi-galereya-detail',
  templateUrl: './galereya-detail.component.html',
})
export class GalereyaDetailComponent implements OnInit {
  galereya: IGalereya | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ galereya }) => (this.galereya = galereya));
  }

  previousState(): void {
    window.history.back();
  }
}
