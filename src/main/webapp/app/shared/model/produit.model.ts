import { IRestaurant } from 'app/shared/model/restaurant.model';
import { IPanier } from 'app/shared/model/panier.model';

export interface IProduit {
  id?: number;
  name?: string;
  price?: number;
  quantity?: number;
  restaurant?: IRestaurant;
  carts?: IPanier[];
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public quantity?: number,
    public restaurant?: IRestaurant,
    public carts?: IPanier[]
  ) {}
}
