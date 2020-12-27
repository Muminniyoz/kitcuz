export interface ITheme {
  id?: number;
  name?: string;
  number?: number;
  isSection?: boolean;
  about?: string;
  homeWorkAbouts?: string;
  fileUrl?: string;
  planningId?: number;
}

export class Theme implements ITheme {
  constructor(
    public id?: number,
    public name?: string,
    public number?: number,
    public isSection?: boolean,
    public about?: string,
    public homeWorkAbouts?: string,
    public fileUrl?: string,
    public planningId?: number
  ) {
    this.isSection = this.isSection || false;
  }
}
