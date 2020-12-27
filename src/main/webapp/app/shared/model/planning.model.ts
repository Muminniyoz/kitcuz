export interface IPlanning {
  id?: number;
  name?: string;
  about?: string;
  duration?: string;
  fileUrl?: string;
  courseId?: number;
  teacherId?: number;
}

export class Planning implements IPlanning {
  constructor(
    public id?: number,
    public name?: string,
    public about?: string,
    public duration?: string,
    public fileUrl?: string,
    public courseId?: number,
    public teacherId?: number
  ) {}
}
