import { ITeacher } from 'app/shared/model/teacher.model';
import { ICourses } from 'app/shared/model/courses.model';

export interface ISkill {
  id?: number;
  name?: string;
  teachers?: ITeacher[];
  courses?: ICourses[];
}

export class Skill implements ISkill {
  constructor(public id?: number, public name?: string, public teachers?: ITeacher[], public courses?: ICourses[]) {}
}
