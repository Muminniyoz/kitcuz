import { Moment } from 'moment';
import { FileGroup } from 'app/shared/model/enumerations/file-group.model';

export interface ISystemFiles {
  id?: number;
  name?: string;
  hashName?: string;
  type?: string;
  fileContentType?: string;
  file?: any;
  time?: Moment;
  fileGroup?: FileGroup;
}

export class SystemFiles implements ISystemFiles {
  constructor(
    public id?: number,
    public name?: string,
    public hashName?: string,
    public type?: string,
    public fileContentType?: string,
    public file?: any,
    public time?: Moment,
    public fileGroup?: FileGroup
  ) {}
}
