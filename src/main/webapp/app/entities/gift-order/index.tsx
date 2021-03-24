import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GiftOrder from './gift-order';
import GiftOrderDetail from './gift-order-detail';
import GiftOrderUpdate from './gift-order-update';
import GiftOrderDeleteDialog from './gift-order-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GiftOrderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GiftOrderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GiftOrderDetail} />
      <ErrorBoundaryRoute path={match.url} component={GiftOrder} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GiftOrderDeleteDialog} />
  </>
);

export default Routes;
