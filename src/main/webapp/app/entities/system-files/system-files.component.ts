import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISystemFiles } from 'app/shared/model/system-files.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SystemFilesService } from './system-files.service';
import { SystemFilesDeleteDialogComponent } from './system-files-delete-dialog.component';

@Component({
  selector: 'jhi-system-files',
  templateUrl: './system-files.component.html',
})
export class SystemFilesComponent implements OnInit, OnDestroy {
  systemFiles: ISystemFiles[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected systemFilesService: SystemFilesService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.systemFiles = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.systemFilesService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ISystemFiles[]>) => this.paginateSystemFiles(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.systemFiles = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSystemFiles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISystemFiles): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInSystemFiles(): void {
    this.eventSubscriber = this.eventManager.subscribe('systemFilesListModification', () => this.reset());
  }

  delete(systemFiles: ISystemFiles): void {
    const modalRef = this.modalService.open(SystemFilesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.systemFiles = systemFiles;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSystemFiles(data: ISystemFiles[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.systemFiles.push(data[i]);
      }
    }
  }
}
