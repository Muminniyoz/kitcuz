import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStudentGroup } from 'app/shared/model/student-group.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { StudentGroupService } from './student-group.service';
import { StudentGroupDeleteDialogComponent } from './student-group-delete-dialog.component';

@Component({
  selector: 'jhi-student-group',
  templateUrl: './student-group.component.html',
})
export class StudentGroupComponent implements OnInit, OnDestroy {
  studentGroups: IStudentGroup[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected studentGroupService: StudentGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.studentGroups = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.studentGroupService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IStudentGroup[]>) => this.paginateStudentGroups(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.studentGroups = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStudentGroups();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStudentGroup): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStudentGroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('studentGroupListModification', () => this.reset());
  }

  delete(studentGroup: IStudentGroup): void {
    const modalRef = this.modalService.open(StudentGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.studentGroup = studentGroup;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateStudentGroups(data: IStudentGroup[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.studentGroups.push(data[i]);
      }
    }
  }
}
