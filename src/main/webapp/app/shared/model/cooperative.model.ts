import { IRestaurant } from 'app/shared/model/restaurant.model';
import { ICompte } from 'app/shared/model/compte.model';

export interface ICooperative {
  id?: number;
  name?: string;
  possessions?: IRestaurant[];
  members?: ICompte[];
}

export class Cooperative implements ICooperative {
  constructor(public id?: number, public name?: string, public possessions?: IRestaurant[], public members?: ICompte[]) {}
}
