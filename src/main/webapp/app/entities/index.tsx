import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Category from './category';
import GiftItem from './gift-item';
import Image from './image';
import Iinventory from './iinventory';
import Order from './order';
import Inventory from './inventory';
import Cart from './cart';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}category`} component={Category} />
      <ErrorBoundaryRoute path={`${match.url}gift-item`} component={GiftItem} />
      <ErrorBoundaryRoute path={`${match.url}image`} component={Image} />
      <ErrorBoundaryRoute path={`${match.url}iinventory`} component={Iinventory} />
      <ErrorBoundaryRoute path={`${match.url}order`} component={Order} />
      <ErrorBoundaryRoute path={`${match.url}inventory`} component={Inventory} />
      <ErrorBoundaryRoute path={`${match.url}cart`} component={Cart} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
