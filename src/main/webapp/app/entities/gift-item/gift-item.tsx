import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './gift-item.reducer';
import { IGiftItem } from 'app/shared/model/gift-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGiftItemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const GiftItem = (props: IGiftItemProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { giftItemList, match, loading } = props;
  return (
    <div>
      <h2 id="gift-item-heading">
        Gift Items
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Gift Item
        </Link>
      </h2>
      <div className="table-responsive">
        {giftItemList && giftItemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Gift Name</th>
                <th>Descripption</th>
                <th>Unit Price</th>
                <th>Avalible Quantity</th>
                <th>Category</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {giftItemList.map((giftItem, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${giftItem.id}`} color="link" size="sm">
                      {giftItem.id}
                    </Button>
                  </td>
                  <td>{giftItem.giftName}</td>
                  <td>{giftItem.descripption}</td>
                  <td>{giftItem.unitPrice}</td>
                  <td>{giftItem.avalibleQuantity}</td>
                  <td>{giftItem.category ? <Link to={`category/${giftItem.category.id}`}>{giftItem.category.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${giftItem.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${giftItem.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${giftItem.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Gift Items found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ giftItem }: IRootState) => ({
  giftItemList: giftItem.entities,
  loading: giftItem.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GiftItem);
