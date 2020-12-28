import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICourseRequests } from 'app/shared/model/course-requests.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CourseRequestsService } from './course-requests.service';
import { CourseRequestsDeleteDialogComponent } from './course-requests-delete-dialog.component';

@Component({
  selector: 'jhi-course-requests',
  templateUrl: './course-requests.component.html',
})
export class CourseRequestsComponent implements OnInit, OnDestroy {
  courseRequests: ICourseRequests[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected courseRequestsService: CourseRequestsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.courseRequests = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.courseRequestsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ICourseRequests[]>) => this.paginateCourseRequests(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.courseRequests = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCourseRequests();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICourseRequests): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCourseRequests(): void {
    this.eventSubscriber = this.eventManager.subscribe('courseRequestsListModification', () => this.reset());
  }

  delete(courseRequests: ICourseRequests): void {
    const modalRef = this.modalService.open(CourseRequestsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.courseRequests = courseRequests;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCourseRequests(data: ICourseRequests[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.courseRequests.push(data[i]);
      }
    }
  }
}
