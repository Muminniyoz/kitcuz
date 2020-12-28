import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFaq } from 'app/shared/model/faq.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FaqService } from './faq.service';
import { FaqDeleteDialogComponent } from './faq-delete-dialog.component';

@Component({
  selector: 'jhi-faq',
  templateUrl: './faq.component.html',
})
export class FaqComponent implements OnInit, OnDestroy {
  faqs: IFaq[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected faqService: FaqService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.faqs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.faqService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IFaq[]>) => this.paginateFaqs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.faqs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFaqs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFaq): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFaqs(): void {
    this.eventSubscriber = this.eventManager.subscribe('faqListModification', () => this.reset());
  }

  delete(faq: IFaq): void {
    const modalRef = this.modalService.open(FaqDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.faq = faq;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFaqs(data: IFaq[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.faqs.push(data[i]);
      }
    }
  }
}
