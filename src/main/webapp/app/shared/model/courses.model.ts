import { ISkill } from 'app/shared/model/skill.model';

export interface ICourses {
  id?: number;
  title?: string;
  about?: any;
  price?: number;
  imageUrl?: string;
  skills?: ISkill[];
}

export class Courses implements ICourses {
  constructor(
    public id?: number,
    public title?: string,
    public about?: any,
    public price?: number,
    public imageUrl?: string,
    public skills?: ISkill[]
  ) {}
}
