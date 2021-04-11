import { IPanier } from 'app/shared/model/panier.model';
import { ICompte } from 'app/shared/model/compte.model';
import { IRestaurant } from 'app/shared/model/restaurant.model';

export interface ICourse {
  id?: number;
  timeRequired?: number;
  order?: IPanier;
  agents?: ICompte[];
  restaurant?: IRestaurant;
}

export class Course implements ICourse {
  constructor(
    public id?: number,
    public timeRequired?: number,
    public order?: IPanier,
    public agents?: ICompte[],
    public restaurant?: IRestaurant
  ) {}
}
