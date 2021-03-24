import { ICategory } from 'app/shared/model/category.model';
import { ICart } from 'app/shared/model/cart.model';
import { IGiftOrder } from 'app/shared/model/gift-order.model';

export interface IGiftItem {
  id?: number;
  giftName?: string;
  descripption?: string;
  unitPrice?: number;
  avalibleQuantity?: number;
  category?: ICategory;
  carts?: ICart[];
  orders?: IGiftOrder[];
}

export const defaultValue: Readonly<IGiftItem> = {};
