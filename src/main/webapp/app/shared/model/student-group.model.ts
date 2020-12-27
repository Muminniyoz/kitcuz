import { Moment } from 'moment';

export interface IStudentGroup {
  id?: number;
  startingDate?: Moment;
  active?: boolean;
  contractNumber?: string;
  studentId?: number;
  groupId?: number;
}

export class StudentGroup implements IStudentGroup {
  constructor(
    public id?: number,
    public startingDate?: Moment,
    public active?: boolean,
    public contractNumber?: string,
    public studentId?: number,
    public groupId?: number
  ) {
    this.active = this.active || false;
  }
}
