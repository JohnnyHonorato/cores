import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class QuickViewService {

  constructor() { }

  getTop(coordinatesCard) {
    if (coordinatesCard.direction === 'UP') {
      return 'unset';
    }
    return coordinatesCard.top.toString() + 'px';
  }

  getLeft(coordinatesCard) {
    if (coordinatesCard.direction === 'UP') {
      return 'unset';
    }
    return coordinatesCard.left.toString() + 'px';
  }

  getRight(coordinatesCard) {
    if (coordinatesCard.direction === 'UP') {
      return '-' + coordinatesCard.left.toString() + 'px';
    }
    return 'unset';
  }

  getBottom(coordinatesCard, dropdown) {
    let elementHeight = 0;
    if (coordinatesCard.direction === 'UP') {
      if (dropdown) {
        elementHeight = dropdown.nativeElement.offsetHeight;
      }
      return '-' + (coordinatesCard.bottom - elementHeight).toString() + 'px';
    } else {
      return 'unset';
    }

  }
}
