import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAbilityStudent } from 'app/shared/model/ability-student.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AbilityStudentService } from './ability-student.service';
import { AbilityStudentDeleteDialogComponent } from './ability-student-delete-dialog.component';

@Component({
  selector: 'jhi-ability-student',
  templateUrl: './ability-student.component.html',
})
export class AbilityStudentComponent implements OnInit, OnDestroy {
  abilityStudents: IAbilityStudent[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected abilityStudentService: AbilityStudentService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.abilityStudents = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.abilityStudentService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IAbilityStudent[]>) => this.paginateAbilityStudents(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.abilityStudents = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAbilityStudents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAbilityStudent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInAbilityStudents(): void {
    this.eventSubscriber = this.eventManager.subscribe('abilityStudentListModification', () => this.reset());
  }

  delete(abilityStudent: IAbilityStudent): void {
    const modalRef = this.modalService.open(AbilityStudentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.abilityStudent = abilityStudent;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAbilityStudents(data: IAbilityStudent[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.abilityStudents.push(data[i]);
      }
    }
  }
}
