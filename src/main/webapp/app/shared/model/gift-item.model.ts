import { ICart } from 'app/shared/model/cart.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IGiftItem {
  id?: number;
  giftName?: string;
  descripption?: string;
  unitPrice?: number;
  avalibleQuantity?: number;
  cart?: ICart;
  category?: ICategory;
}

export const defaultValue: Readonly<IGiftItem> = {};
