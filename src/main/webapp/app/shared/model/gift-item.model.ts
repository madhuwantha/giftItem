import { ICategory } from 'app/shared/model/category.model';

export interface IGiftItem {
  id?: number;
  giftName?: string;
  descripption?: string;
  unitPrice?: number;
  category?: ICategory;
}

export const defaultValue: Readonly<IGiftItem> = {};
